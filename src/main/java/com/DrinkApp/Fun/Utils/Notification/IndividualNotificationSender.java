package com.DrinkApp.Fun.Utils.Notification;

import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Enums.NotificationType;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class IndividualNotificationSender implements NotificationSender {

    private final NotificationRepo notificationRepo;

    @Override
    public String getType() {
        return "INDIVIDUAL";
    }

    @Override
    public void send(UserEntity sender, String message, List<UserEntity> recipients) {
        System.out.println("Fix in send");
        if (recipients == null || recipients.isEmpty()) return;

        UserEntity recipient = recipients.getFirst();

        notificationRepo.save(NotificationEntity.builder()
                .sender(sender)
                .recipient(recipient)
                .message(message)
                .type(NotificationType.FRIEND_REQUEST)
                .isRead(false)
                .build());

    }
}
