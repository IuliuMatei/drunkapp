package com.DrinkApp.Fun.Service.Interfaces;

import com.DrinkApp.Fun.Dto.PostDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PostService {

    public List<PostDto> getAllPosts(UserDetails userDetails);
}
