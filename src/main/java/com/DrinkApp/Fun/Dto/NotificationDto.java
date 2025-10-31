package com.DrinkApp.Fun.Dto;

import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private Long id;

    private UserEntity recipient;
    private UserEntity sender;
    private NotificationType type;
    private Long referenceId;
    private String message;
    private Boolean isRead;
    private LocalDateTime createdAt;

}


