package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Entity.FriendshipEntity;
import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Utils.Enums.NotificationType;
import com.DrinkApp.Fun.Utils.Enums.Status;
import com.DrinkApp.Fun.Repository.FriendshipRepo;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Implementation.FriendshipServiceImpl;
import com.DrinkApp.Fun.Service.Interfaces.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FriendshipServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private FriendshipRepo friendshipRepo;

    @Mock
    private NotificationRepo notificationRepo;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    private UserEntity requester;
    private UserEntity receiver;
    private UserDetails requesterDetails;
    private UserDetails receiverDetails;
    private FriendshipEntity friendship;
    private NotificationEntity notification;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requester = UserEntity.builder()
                .id(1L)
                .uname("john")
                .email("john@example.com")
                .build();

        receiver = UserEntity.builder()
                .id(2L)
                .uname("mike")
                .email("mike@example.com")
                .build();

        requesterDetails = User.withUsername("john@example.com")
                .password("pass")
                .roles("USER")
                .build();

        receiverDetails = User.withUsername("mike@example.com")
                .password("pass")
                .roles("USER")
                .build();

        friendship = FriendshipEntity.builder()
                .id(10L)
                .requester(requester)
                .receiver(receiver)
                .status(Status.PENDING)
                .build();

        notification = NotificationEntity.builder()
                .id(100L)
                .sender(requester)
                .recipient(receiver)
                .type(NotificationType.FRIEND_REQUEST)
                .referenceId(friendship.getId())
                .isRead(false)
                .build();
    }


    @Test
    void givenValidUsers_whenSendFriendRequest_thenCreatesFriendshipAndNotification() {
        when(userRepo.findByUname("mike")).thenReturn(Optional.of(receiver));
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(requester));
        when(friendshipRepo.findByRequesterAndReceiver(requester, receiver)).thenReturn(Optional.empty());

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "mike");

        assertEquals(200, response.getStatusCodeValue());
        verify(friendshipRepo, times(1)).save(any(FriendshipEntity.class));
        verify(notificationService, times(1))
                .sendNotification(eq("john@example.com"), anyString(), eq("INDIVIDUAL"), eq(List.of(receiver)));
    }

    @Test
    void givenReceiverNotFound_whenSendFriendRequest_thenReturns404() {
        when(userRepo.findByUname("ghost")).thenReturn(Optional.empty());

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "ghost");

        assertEquals(404, response.getStatusCodeValue());
        verify(friendshipRepo, never()).save(any());
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void givenRequesterNotFound_whenSendFriendRequest_thenReturns401() {
        when(userRepo.findByUname("mike")).thenReturn(Optional.of(receiver));
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.empty());

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "mike");

        assertEquals(401, response.getStatusCodeValue());
        verify(friendshipRepo, never()).save(any());
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void givenSameUser_whenSendFriendRequest_thenReturns400() {
        when(userRepo.findByUname("john")).thenReturn(Optional.of(requester));
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(requester));

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "john");

        assertEquals(400, response.getStatusCodeValue());
        verify(friendshipRepo, never()).save(any());
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void givenExistingFriendship_whenSendFriendRequest_thenReturns409() {
        when(userRepo.findByUname("mike")).thenReturn(Optional.of(receiver));
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(requester));
        when(friendshipRepo.findByRequesterAndReceiver(requester, receiver))
                .thenReturn(Optional.of(friendship));

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "mike");

        assertEquals(409, response.getStatusCodeValue());
        verify(friendshipRepo, never()).save(any());
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void givenValidFriendRequest_whenUserAccepts_thenStatusAcceptedAndNotificationRead() {
        when(notificationRepo.findById(notification.getId())).thenReturn(Optional.of(notification));
        when(userRepo.findByEmail(receiver.getEmail())).thenReturn(Optional.of(receiver));
        when(friendshipRepo.findById(friendship.getId())).thenReturn(Optional.of(friendship));

        boolean result = friendshipService.acceptFriendRequest(receiverDetails, notification.getId());

        assertTrue(result);
        assertEquals(Status.ACCEPTED, friendship.getStatus());
        assertTrue(notification.getIsRead());
        verify(friendshipRepo, times(1)).save(friendship);
        verify(notificationRepo, times(1)).save(notification);
        verify(notificationService, times(1))
                .sendNotification(eq(receiver.getEmail()), anyString(), eq("INDIVIDUAL"), eq(List.of(requester)));
    }

    @Test
    void givenInvalidNotificationId_whenAcceptCalled_thenReturnsFalse() {
        when(notificationRepo.findById(999L)).thenReturn(Optional.empty());

        boolean result = friendshipService.acceptFriendRequest(receiverDetails, 999L);

        assertFalse(result);
        verifyNoInteractions(friendshipRepo, notificationService);
    }

    @Test
    void givenNonFriendRequestNotification_whenAcceptCalled_thenReturnsFalse() {
        notification.setType(NotificationType.DONATION);
        when(notificationRepo.findById(notification.getId())).thenReturn(Optional.of(notification));

        boolean result = friendshipService.acceptFriendRequest(receiverDetails, notification.getId());

        assertFalse(result);
        verifyNoInteractions(friendshipRepo, notificationService);
    }

    @Test
    void givenValidFriendRequest_whenUserDeclines_thenStatusDeclinedAndNotificationRead() {
        when(notificationRepo.findById(notification.getId())).thenReturn(Optional.of(notification));
        when(userRepo.findByEmail(receiver.getEmail())).thenReturn(Optional.of(receiver));
        when(friendshipRepo.findById(friendship.getId())).thenReturn(Optional.of(friendship));

        boolean result = friendshipService.declineFriendRequest(receiverDetails, notification.getId());

        assertTrue(result);
        assertEquals(Status.DECLINED, friendship.getStatus());
        assertTrue(notification.getIsRead());
        verify(friendshipRepo, times(1)).save(friendship);
        verify(notificationRepo, times(1)).save(notification);
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void givenNotificationBelongsToDifferentUser_whenDeclineCalled_thenReturnsFalse() {
        UserEntity otherUser = UserEntity.builder().id(5L).uname("tom").email("tom@example.com").build();
        when(userRepo.findByEmail("tom@example.com")).thenReturn(Optional.of(otherUser));
        when(notificationRepo.findById(notification.getId())).thenReturn(Optional.of(notification));

        UserDetails otherDetails = User.withUsername("tom@example.com").password("p").roles("USER").build();

        boolean result = friendshipService.declineFriendRequest(otherDetails, notification.getId());

        assertFalse(result);
        verify(friendshipRepo, never()).save(any());
        verify(notificationRepo, never()).save(any());
    }
}
