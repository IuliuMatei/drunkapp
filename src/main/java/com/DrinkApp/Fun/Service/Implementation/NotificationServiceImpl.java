package com.DrinkApp.Fun.Service.Implementation;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.NotificationMapper;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Interfaces.NotificationService;
import com.DrinkApp.Fun.Utils.Notification.NotificationSender;
import com.DrinkApp.Fun.Utils.Notification.NotificationSenderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;
    private final UserRepo userRepo;
    private final NotificationSenderFactory notificationSenderFactory;



    @Override
    public List<NotificationDto> getAll(UserDetails userDetails) {
        Optional<UserEntity> recipient = userRepo.findByEmail(userDetails.getUsername());

        if (recipient.isEmpty()){
            return List.of();
        }

        return notificationRepo.getAllByRecipient(recipient.get()).stream().map(notificationMapper::entityToDto).collect(Collectors.toList());
    }

    @Override
    public boolean markRead(UserDetails userDetails) {
        Optional<UserEntity> currentUser = userRepo.findByEmail(userDetails.getUsername());

        if (currentUser.isEmpty()){
            return false;
        }

        notificationRepo.markAllNotificationsRead(currentUser.get());

        return true;
    }

    @Override
    public void sendNotification(String senderEmail, String message, String type, List<UserEntity> recipients) {
        UserEntity sender = userRepo.findByEmail(senderEmail)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found: " + senderEmail));

        NotificationSender senderStrategy = notificationSenderFactory.getSender(type);

        System.out.println("Inainte de send");

        senderStrategy.send(sender, message, recipients);
    }
}
