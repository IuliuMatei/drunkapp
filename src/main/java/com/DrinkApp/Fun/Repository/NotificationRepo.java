package com.DrinkApp.Fun.Repository;

import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {

    List<NotificationEntity> getAllByRecipient(UserEntity userEntity);
}
