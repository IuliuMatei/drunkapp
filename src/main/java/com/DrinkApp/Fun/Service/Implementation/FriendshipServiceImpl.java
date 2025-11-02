package com.DrinkApp.Fun.Service.Implementation;

import com.DrinkApp.Fun.Entity.FriendshipEntity;
import com.DrinkApp.Fun.Entity.NotificationEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Repository.NotificationRepo;
import com.DrinkApp.Fun.Utils.Enums.NotificationType;
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
    private final NotificationRepo notificationRepo;

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

        String message = requester.get().getUname() + " send you a friend request";
        notificationService.sendNotification(
                requester.get().getEmail(),
                message,
                "INDIVIDUAL",
                List.of(receiver.get())
        );

        return ResponseEntity.ok().build();
    }

    @Override
    public boolean acceptFriendRequest(UserDetails userDetails, Long referenceId) {

        Optional<NotificationEntity> notification = notificationRepo.findById(referenceId);
        if (notification.isEmpty()){
            return false;
        }

        NotificationEntity notificationEntity = notification.get();
        if (!notificationEntity.getType().equals(NotificationType.FRIEND_REQUEST)){
            return false;
        }

        Optional<UserEntity> currentUser = userRepo.findByEmail(userDetails.getUsername());
        if (currentUser.isEmpty() || !notificationEntity.getRecipient().getId().equals(currentUser.get().getId())) {
            return false;
        }

        Optional<FriendshipEntity> friendshipOpt = friendshipRepo.findById(notificationEntity.getReferenceId());
        if (friendshipOpt.isEmpty()) return false;

        FriendshipEntity friendship = friendshipOpt.get();
        friendship.setStatus(Status.ACCEPTED);
        friendshipRepo.save(friendship);

        notificationEntity.setIsRead(true);
        notificationRepo.save(notificationEntity);

        notificationService.sendNotification(
                currentUser.get().getEmail(),
                currentUser.get().getUname() + " ți-a acceptat cererea de prietenie",
                "INDIVIDUAL",
                List.of(notificationEntity.getSender())
        );

        return true;
    }

    @Override
    public boolean declineFriendRequest(UserDetails userDetails, Long referenceId) {
        Optional<NotificationEntity> notificationOpt = notificationRepo.findById(referenceId);
        if (notificationOpt.isEmpty()) return false;

        NotificationEntity notification = notificationOpt.get();
        if (notification.getType() != NotificationType.FRIEND_REQUEST) return false;

        Optional<UserEntity> currentUser = userRepo.findByEmail(userDetails.getUsername());
        if (currentUser.isEmpty() || !notification.getRecipient().getId().equals(currentUser.get().getId())) {
            return false;
        }

        Optional<FriendshipEntity> friendshipOpt = friendshipRepo.findById(notification.getReferenceId());
        if (friendshipOpt.isEmpty()) return false;

        FriendshipEntity friendship = friendshipOpt.get();
        friendship.setStatus(Status.DECLINED);
        friendshipRepo.save(friendship);

        notification.setIsRead(true);
        notificationRepo.save(notification);

        return true;
    }

    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepo.findByUname(username);
    }

    public Optional<UserEntity> getUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }
}
