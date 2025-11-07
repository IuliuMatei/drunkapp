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
import com.DrinkApp.Fun.Utils.Exceptions.*;
import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public ResponseEntity<String> sendFriendshipRequest(UserDetails userDetails, String username) {

        UserEntity receiver = getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));

        UserEntity requester = getUserByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);

        if (receiver.getId().equals(requester.getId())) {
            throw new SameUserFriendshipException();
        }

        if (friendshipRepo.findByRequesterAndReceiver(requester, receiver).isPresent()) {
            throw  new FriendshipAlreadyRequestedException();
        }

        FriendshipEntity friendshipEntity = FriendshipEntity.builder()
                .requester(requester)
                .receiver(receiver)
                .status(Status.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        friendshipRepo.save(friendshipEntity);

        String message = requester.getUname() + " send you a friend request";

        NotificationEntity newNotification = NotificationEntity.builder()
                .type(NotificationType.FRIEND_REQUEST)
                .isRead(false)
                .message(message)
                .sender(requester)
                .recipient(receiver)
                .createdAt(LocalDateTime.now())
                .referenceId(friendshipEntity.getId())
                .build();

        notificationRepo.save(newNotification);

        return ResponseEntity.ok().build();
    }

    @Transactional
    @Override
    public boolean acceptFriendRequest(UserDetails userDetails, Long referenceId) {

        NotificationEntity notification = notificationRepo.findById(referenceId).orElseThrow(NotificationNotFoundException::new);

        if (!notification.getType().equals(NotificationType.FRIEND_REQUEST)){
            throw new NotificationNotFriendRequest();
        }

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        if (!notification.getRecipient().getId().equals(currentUser.getId())) {
            throw new SameUserFriendshipException();
        }

        FriendshipEntity friendship = friendshipRepo.findById(notification.getReferenceId()).orElseThrow(FriendshipNotFoundException::new);

        friendship.setStatus(Status.ACCEPTED);
        friendshipRepo.save(friendship);

        notification.setIsRead(true);
        notificationRepo.save(notification);

        return true;
    }

    @Transactional
    @Override
    public boolean declineFriendRequest(UserDetails userDetails, Long referenceId)
    {
        NotificationEntity notification = notificationRepo.findById(referenceId).orElseThrow(NotificationNotFoundException::new);

        if (notification.getType() != NotificationType.FRIEND_REQUEST){
            throw new NotificationNotFriendRequest();
        }

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(UserNotFoundException::new);
        if (!notification.getRecipient().getId().equals(currentUser.getId())) {
            throw new SameUserFriendshipException();
        }

        FriendshipEntity friendship = friendshipRepo.findById(notification.getReferenceId()).orElseThrow(FriendshipNotFoundException::new);
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
