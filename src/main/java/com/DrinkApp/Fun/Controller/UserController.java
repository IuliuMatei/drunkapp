package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Dto.UserDto;
import com.DrinkApp.Fun.Service.Interfaces.PostService;
import com.DrinkApp.Fun.Service.UserService;
import com.DrinkApp.Fun.Utils.Response.ImageUploadResponse;
import com.DrinkApp.Fun.Utils.Response.UserProfilePictureResponse;
import com.DrinkApp.Fun.Utils.Response.UserProfileResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PostService postService;

    @PostMapping("/{username}/save-photo")
    public ResponseEntity<?> postProfilePicture(@AuthenticationPrincipal UserDetails userDetails,
                                                @NotNull
                                                @RequestParam("image") MultipartFile image) throws IOException
    {

        ImageUploadResponse imageUploadResponse = userService.saveProfilePicture(userDetails, image);

        return ResponseEntity.status(HttpStatus.OK).body(imageUploadResponse);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserProfileResponse> getUserFeed(@AuthenticationPrincipal UserDetails userDetails,
                                                           @PathVariable String username)
    {
        UserProfileResponse userProfileResponse = postService.getFeedByUser(username);

        return ResponseEntity.status(HttpStatus.OK).body(userProfileResponse);
    }

    @GetMapping("/profile-picture")
    public ResponseEntity<?> getProfilePicture(@AuthenticationPrincipal UserDetails userDetails) {

        UserProfilePictureResponse userProfilePictureResponse = userService.findUsernameAndImage(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(userProfilePictureResponse);

    }

}
