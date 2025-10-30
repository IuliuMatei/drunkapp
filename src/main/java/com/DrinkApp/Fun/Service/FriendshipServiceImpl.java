package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Entity.FriendshipEntity;
import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Enums.NotificationType;
import com.DrinkApp.Fun.Enums.Status;
import com.DrinkApp.Fun.Repository.FriendshipRepo;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService{

    final FriendshipRepo friendshipRepo;
    final UserRepo userRepo;
    final NotificationRepo notificationRepo;

    @Override
    public ResponseEntity<String> sendFriendshipRequest(UserDetails userDetails, String username) {

        Optional<UserEntity> receiver = getUserByUsername(username);

        if (receiver.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<UserEntity> requester = getUserByEmail(userDetails.getUsername());

        if (requester.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (receiver.get().getId().equals(requester.get().getId())){
            return ResponseEntity.badRequest().build();
        }

        FriendshipEntity friendshipEntity = FriendshipEntity.builder()
                .requester(requester.get())
                .receiver(receiver.get())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        if (friendshipRepo.findByRequesterAndReceiver(requester.get(), receiver.get()).isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        friendshipRepo.save(friendshipEntity);

        NotificationEntity notificationEntity = NotificationEntity.builder()
                .sender(requester.get())
                .type(NotificationType.FRIEND_REQUEST)
                .isRead(false)
                .recipient(receiver.get())
                .referenceId(friendshipEntity.getId())
                .message(requester.get().getUname().concat(" sent you a friend request"))
                .createdAt(LocalDateTime.now())
                .type(NotificationType.FRIEND_REQUEST)
                .build();

        notificationRepo.save(notificationEntity);

        return ResponseEntity.ok().build();
    }

    public Optional<UserEntity> getUserByUsername(String username){
        return userRepo.findByUname(username);
    }

    public Optional<UserEntity> getUserByEmail(String email){
        return userRepo.findByEmail(email);
    }
}
