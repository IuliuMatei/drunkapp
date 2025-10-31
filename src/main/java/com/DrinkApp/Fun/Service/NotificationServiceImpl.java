package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.NotificationMapper;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{

    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;
    private final UserRepo userRepo;


    @Override
    public List<NotificationDto> getAll(UserDetails userDetails) {
        Optional<UserEntity> recipient = userRepo.findByEmail(userDetails.getUsername());

        if (recipient.isEmpty()){
            return List.of();
        }

        return notificationRepo.getAllByRecipient(recipient.get()).stream().map(notificationMapper::entityToDto).collect(Collectors.toList());
    }
}
