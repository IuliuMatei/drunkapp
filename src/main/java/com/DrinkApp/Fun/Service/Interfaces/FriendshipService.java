package com.DrinkApp.Fun.Service.Interfaces;

import com.DrinkApp.Fun.Entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface FriendshipService {


    ResponseEntity<String> sendFriendshipRequest(UserDetails userDetails, String username);

    Optional<UserEntity> getUserByUsername(String username);

    Optional<UserEntity> getUserByEmail(String email);

    boolean acceptFriendRequest(UserDetails userDetails, Long referenceId);

    boolean declineFriendRequest(UserDetails userDetails, Long referenceId);
}
