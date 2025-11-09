package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Service.Interfaces.PostService;
import com.DrinkApp.Fun.Utils.Enums.DrinkName;
import com.DrinkApp.Fun.Utils.Response.PostUploadResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
@Validated // arunca ConstraintViolationException daca constrangerile nu sunt validate
public class PostController {

    private final PostService postService;

    @GetMapping("/feed")
    public ResponseEntity<List<PostDto>> getAllPosts(@AuthenticationPrincipal UserDetails userDetails) {

        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts(userDetails));

    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> newPost(@AuthenticationPrincipal UserDetails userDetails,
                                        @NotBlank
                                        @RequestParam("description") String description,
                                        @NotNull
                                        @RequestParam("drink") DrinkName drinkName,
                                        @NotNull
                                        @RequestParam("image") MultipartFile image) throws IOException {

        PostUploadResponse postUploadResponse = postService.savePost(userDetails, description, drinkName, image);

        return ResponseEntity.status(HttpStatus.CREATED).body(postUploadResponse);

    }
}
