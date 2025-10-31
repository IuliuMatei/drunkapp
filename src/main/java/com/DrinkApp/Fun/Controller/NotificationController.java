package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/notifications")
public class NotificationController {

    NotificationService notificationService;

    @GetMapping("/all")
    public List<NotificationDto> getAll(@AuthenticationPrincipal UserDetails userDetails){
        return notificationService.getAll(userDetails);
    }

}
