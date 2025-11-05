package com.DrinkApp.Fun.Service.Implementation;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.NotificationMapper;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Interfaces.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

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

    @Transactional
    @Override
    public boolean markRead(UserDetails userDetails) {
        Optional<UserEntity> currentUser = userRepo.findByEmail(userDetails.getUsername());

        if (currentUser.isEmpty()){
            return false;
        }

        notificationRepo.markAllNotificationsRead(currentUser.get());

        return true;
    }

    @Override
    public Long getNumberNotificationsNotSeen(UserDetails userDetails)
    {
        UserEntity currentUser = userRepo.findByUname(userDetails.getUsername()).orElseThrow();

        return notificationRepo.countByRecipientAndIsReadFalse(currentUser);
    }
}
