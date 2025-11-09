package com.DrinkApp.Fun.Service.Interfaces;

import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Utils.Response.FriendRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface FriendshipService {


    FriendRequestResponse sendFriendshipRequest(UserDetails userDetails, String username);

    Optional<UserEntity> getUserByUsername(String username);

    Optional<UserEntity> getUserByEmail(String email);

    FriendRequestResponse acceptFriendRequest(UserDetails userDetails, Long referenceId);

    FriendRequestResponse declineFriendRequest(UserDetails userDetails, Long referenceId);
}
