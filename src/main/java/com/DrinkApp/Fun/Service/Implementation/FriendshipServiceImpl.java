package com.DrinkApp.Fun.Service.Implementation;

import com.DrinkApp.Fun.Entity.FriendshipEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Utils.Enums.Status;
import com.DrinkApp.Fun.Repository.FriendshipRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Interfaces.FriendshipService;
import com.DrinkApp.Fun.Service.Interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepo friendshipRepo;
    private final UserRepo userRepo;
    private final NotificationService notificationService;

    @Override
    public ResponseEntity<String> sendFriendshipRequest(UserDetails userDetails, String username) {

        Optional<UserEntity> receiver = getUserByUsername(username);
        if (receiver.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<UserEntity> requester = getUserByEmail(userDetails.getUsername());
        if (requester.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (receiver.get().getId().equals(requester.get().getId())) {
            return ResponseEntity.badRequest().build();
        }

        if (friendshipRepo.findByRequesterAndReceiver(requester.get(), receiver.get()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        FriendshipEntity friendshipEntity = FriendshipEntity.builder()
                .requester(requester.get())
                .receiver(receiver.get())
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        friendshipRepo.save(friendshipEntity);

        String message = requester.get().getUname() + " ți-a trimis o cerere de prietenie";
        notificationService.sendNotification(
                requester.get().getEmail(),
                message,
                "INDIVIDUAL",
                List.of(receiver.get())
        );

        return ResponseEntity.ok().build();
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepo.findByUname(username);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
