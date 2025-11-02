package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Service.Interfaces.FriendshipService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<String> sendFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                                                    @RequestParam String username) {
        return friendShipService.sendFriendshipRequest(userDetails, username);
    }

    @PutMapping("/accept/{referenceId}")
    public ResponseEntity<Void> acceptFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable Long referenceId) {

        boolean success = friendShipService.acceptFriendRequest(userDetails, referenceId);

        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PutMapping("/decline/{referenceId}")
    public ResponseEntity<Void> declineFriendRequest(@AuthenticationPrincipal UserDetails userDetails,
                                                    @PathVariable Long referenceId) {

        boolean success = friendShipService.declineFriendRequest(userDetails, referenceId);

        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }


}
