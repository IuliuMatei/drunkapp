package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Entity.PostEntity;
import com.DrinkApp.Fun.Service.Interfaces.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostService postService;

    @GetMapping("/feed")
    public List<PostDto> getAllPosts(@AuthenticationPrincipal UserDetails userDetails){

        return postService.getAllPosts(userDetails);

    }
}
