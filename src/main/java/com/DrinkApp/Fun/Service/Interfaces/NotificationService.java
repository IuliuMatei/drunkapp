package com.DrinkApp.Fun.Service.Interfaces;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Utils.Exceptions.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getAll(UserDetails userDetails) throws UserNotFoundException;

    boolean markRead(UserDetails userDetails) throws UserNotFoundException;

    Long getNumberNotificationsNotSeen(UserDetails userDetails) throws UserNotFoundException;
}
