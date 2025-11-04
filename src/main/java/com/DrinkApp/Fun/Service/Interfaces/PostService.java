package com.DrinkApp.Fun.Service.Interfaces;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Utils.Enums.DrinkName;
import com.DrinkApp.Fun.Utils.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    public List<PostDto> getAllPosts(UserDetails userDetails);

    boolean savePost(UserDetails userDetails, String description, DrinkName drinkName, MultipartFile image) throws IOException;

    UserProfileResponse getFeedByUser(String username);
}
