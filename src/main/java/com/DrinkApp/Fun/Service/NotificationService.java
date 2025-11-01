package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Dto.NotificationDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getAll(UserDetails userDetails);

    boolean markRead(UserDetails userDetails);
}
