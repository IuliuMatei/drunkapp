package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Service.Interfaces.FriendshipService;
import com.DrinkApp.Fun.Utils.Response.FriendRequestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/friendship")
@RestController
@RequiredArgsConstructor
public class FriendshipController {

    final FriendshipService friendShipService;

    @PostMapping("/request")
    public ResponseEntity<?> sendFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestParam String username) {
        FriendRequestResponse friendRequestResponse = friendShipService.sendFriendshipRequest(userDetails, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(friendRequestResponse);
    }

    @PutMapping("/accept/{referenceId}")
    public ResponseEntity<?> acceptFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable Long referenceId) {

        FriendRequestResponse friendRequestResponse = friendShipService.acceptFriendRequest(userDetails, referenceId);

        return ResponseEntity.status(HttpStatus.CREATED).body(friendRequestResponse);
    }

    @PutMapping("/decline/{referenceId}")
    public ResponseEntity<?> declineFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable Long referenceId) {

        FriendRequestResponse friendRequestResponse = friendShipService.declineFriendRequest(userDetails, referenceId);

        return ResponseEntity.status(HttpStatus.CREATED).body(friendRequestResponse);
    }


}
