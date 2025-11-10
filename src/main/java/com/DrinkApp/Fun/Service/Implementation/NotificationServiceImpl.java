package com.DrinkApp.Fun.Service.Implementation;

import com.DrinkApp.Fun.Dto.NotificationDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.NotificationMapper;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Interfaces.NotificationService;
import com.DrinkApp.Fun.Utils.Exceptions.CurrentUserNotFoundException;
import com.DrinkApp.Fun.Utils.Exceptions.UserNotFoundException;
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
    public List<NotificationDto> getAll(UserDetails userDetails) throws UserNotFoundException {

        UserEntity recipient = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        return notificationRepo.getAllByRecipient(recipient).stream().map(notificationMapper::entityToDto).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean markRead(UserDetails userDetails) throws UserNotFoundException {

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        notificationRepo.markAllNotificationsRead(currentUser);

        return true;
    }

    @Override
    public Long getNumberNotificationsNotSeen(UserDetails userDetails) throws UserNotFoundException {
        UserEntity currentUser = userRepo.findByUname(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        return notificationRepo.countByRecipientAndIsReadFalse(currentUser);
    }
}
