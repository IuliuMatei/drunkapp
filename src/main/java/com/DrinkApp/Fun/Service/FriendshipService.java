package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Entity.UserEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface FriendshipService {


    ResponseEntity<String> sendFriendshipRequest(UserDetails userDetails, String username);

    Optional<UserEntity> getUserByUsername(String username);

    Optional<UserEntity> getUserByEmail(String email);
}
