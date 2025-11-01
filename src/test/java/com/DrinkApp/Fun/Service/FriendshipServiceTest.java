package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Entity.FriendshipEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Enums.Status;
import com.DrinkApp.Fun.Repository.FriendshipRepo;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FriendshipServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private FriendshipRepo friendshipRepo;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private FriendshipServiceImpl friendshipService;

    private UserEntity requester;
    private UserEntity receiver;
    private UserDetails requesterDetails;

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
    }

    @Test
    void testSendFriendRequest_Success() {
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
    void testSendFriendRequest_UserNotFound() {
        when(userRepo.findByUname("ghost")).thenReturn(Optional.empty());

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "ghost");

        assertEquals(404, response.getStatusCodeValue());
        verify(friendshipRepo, never()).save(any());
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void testSendFriendRequest_RequesterNotFound() {
        when(userRepo.findByUname("mike")).thenReturn(Optional.of(receiver));
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.empty());

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "mike");

        assertEquals(401, response.getStatusCodeValue());
        verify(friendshipRepo, never()).save(any());
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void testSendFriendRequest_SameUser() {
        when(userRepo.findByUname("john")).thenReturn(Optional.of(requester));
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(requester));

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "john");

        assertEquals(400, response.getStatusCodeValue());
        verify(friendshipRepo, never()).save(any());
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }

    @Test
    void testSendFriendRequest_AlreadyExists() {
        when(userRepo.findByUname("mike")).thenReturn(Optional.of(receiver));
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(requester));
        when(friendshipRepo.findByRequesterAndReceiver(requester, receiver))
                .thenReturn(Optional.of(new FriendshipEntity()));

        ResponseEntity<String> response = friendshipService.sendFriendshipRequest(requesterDetails, "mike");

        assertEquals(409, response.getStatusCodeValue());
        verify(friendshipRepo, never()).save(any());
        verify(notificationService, never()).sendNotification(any(), any(), any(), any());
    }
}
