package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Service.Interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    @PutMapping("markReadAll")
    public ResponseEntity<Void> markReadAll(@AuthenticationPrincipal UserDetails userDetails){

        if (!notificationService.markRead(userDetails)){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }



}
