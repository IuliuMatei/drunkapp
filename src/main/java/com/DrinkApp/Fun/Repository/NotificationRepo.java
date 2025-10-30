package com.DrinkApp.Fun.Repository;

import com.DrinkApp.Fun.Entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepo extends JpaRepository<NotificationEntity, Long> {
}
