package com.DrinkApp.Fun.Utils.Notification;

import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BroadcastNotificationSender implements NotificationSender {

    private final NotificationRepo notificationRepo;
    private final JavaMailSender mailSender;

    @Override
    public String getType() {
        return "BROADCAST";
    }

    @Async("notificationExecutor")
    @Override
    public void send(UserEntity sender, String message, List<UserEntity> recipients) {

        for (UserEntity recipient : recipients) {
            notificationRepo.save(NotificationEntity.builder()
                    .sender(sender)
                    .recipient(recipient)
                    .message(message)
                    .isRead(false)
                    .build());

            try {
                SimpleMailMessage mail = new SimpleMailMessage();
                mail.setTo(recipient.getEmail());
                mail.setSubject("Notificare nouă de la " + sender.getUname());
                mail.setText(message);
                mailSender.send(mail);
            } catch (Exception e) {

            }
        }

    }
}
