package com.DrinkApp.Fun.Repository;

import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> getAllByRecipient(UserEntity userEntity);


    @Modifying(clearAutomatically = true)
    @Query("UPDATE NotificationEntity n SET n.isRead = true WHERE n.recipient = :recipient AND n.isRead = false")
    void markAllNotificationsRead(@Param("recipient") UserEntity recipient);

    long countByRecipientAndIsReadFalse(UserEntity recipient);
}
