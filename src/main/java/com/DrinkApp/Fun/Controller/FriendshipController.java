package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Service.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/friendship")
@RestController
@RequiredArgsConstructor
public class FriendshipController {

    final FriendshipService friendShipService;

    @PostMapping("/request")
    public ResponseEntity<String> sendFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestParam String username) {
        return friendShipService.sendFriendshipRequest(userDetails, username);
    }

}
