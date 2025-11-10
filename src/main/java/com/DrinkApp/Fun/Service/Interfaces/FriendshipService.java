package com.DrinkApp.Fun.Service.Interfaces;

import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Utils.Exceptions.FriendshipAlreadyRequestedException;
import com.DrinkApp.Fun.Utils.Exceptions.SameUserFriendshipException;
import com.DrinkApp.Fun.Utils.Exceptions.UserNotFoundException;
import com.DrinkApp.Fun.Utils.Response.FriendRequestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface FriendshipService {


    FriendRequestResponse sendFriendshipRequest(UserDetails userDetails, String username) throws UserNotFoundException, SameUserFriendshipException, FriendshipAlreadyRequestedException;

    Optional<UserEntity> getUserByUsername(String username);

    Optional<UserEntity> getUserByEmail(String email);

    FriendRequestResponse acceptFriendRequest(UserDetails userDetails, Long referenceId) throws UserNotFoundException, SameUserFriendshipException;

    FriendRequestResponse declineFriendRequest(UserDetails userDetails, Long referenceId) throws UserNotFoundException, SameUserFriendshipException;
}
