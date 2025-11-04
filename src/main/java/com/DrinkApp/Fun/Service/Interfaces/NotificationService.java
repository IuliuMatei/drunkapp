package com.DrinkApp.Fun.Service.Interfaces;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getAll(UserDetails userDetails);

    boolean markRead(UserDetails userDetails);

    Long getNumberNotificationsNotSeen(UserDetails userDetails);
}
