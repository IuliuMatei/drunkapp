package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Enums.Role;
import com.DrinkApp.Fun.Mapper.NotificationMapper;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class NotificationServiceTest {

    @Mock
    NotificationRepo notificationRepo;

    @Mock
    NotificationMapper notificationMapper;

    @Mock
    UserRepo userRepo;

    @InjectMocks
    NotificationServiceImpl notificationService;

    private UserDetails userDetails;
    private UserEntity userEntity;

    @BeforeEach
    void setUp(){

        MockitoAnnotations.openMocks(this);

        userDetails = User.withUsername("john@example.com")
                .password("pass")
                .roles("USER")
                .build();
        userEntity = UserEntity.builder()
                .id(2L)
                .uname("Mattheu")
                .role(Role.USER)
                .email("matei_vitan@yahoo.ro")
                .createdAt(LocalDateTime.now())
                .password("123")
                .build();
    }

    @Test
    void givenUserNotFound_whenFindAllNotifications_thenEmptyList(){
        Mockito.when(userRepo.findByEmail(userDetails.getUsername())).thenReturn(Optional.empty());

        List<NotificationDto> allNotifications = notificationService.getAll(userDetails);

        assertNotNull(allNotifications);
        assertTrue(allNotifications.isEmpty());
        verify(userRepo, times(1)).findByEmail("john@example.com");
        verifyNoMoreInteractions(notificationRepo, notificationMapper);

    }

    @Test
    void givenUserFound_whenGetAllNotifications_thenNotEmptyList(){

        NotificationEntity notif1 = NotificationEntity.builder()
                .id(1L)
                .message("Salut!")
                .createdAt(LocalDateTime.now())
                .build();

        NotificationDto dto1 = NotificationDto.builder()
                .id(1L)
                .message("Salut!")
                .build();

        // 🔹 comportament mock-uri
        when(userRepo.findByEmail("john@example.com")).thenReturn(Optional.of(userEntity));
        when(notificationRepo.getAllByRecipient(userEntity)).thenReturn(List.of(notif1));
        when(notificationMapper.entityToDto(notif1)).thenReturn(dto1);

        // 🔹 apelăm metoda reală
        List<NotificationDto> result = notificationService.getAll(userDetails);

        assertEquals(1, result.size());
        assertEquals("Salut!", result.get(0).getMessage());

        verify(userRepo, times(1)).findByEmail("john@example.com");
        verify(notificationRepo, times(1)).getAllByRecipient(userEntity);
        verify(notificationMapper, times(1)).entityToDto(notif1);

    }
}
