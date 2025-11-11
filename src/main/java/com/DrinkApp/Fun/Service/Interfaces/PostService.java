package com.DrinkApp.Fun.Service.Interfaces;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Utils.Enums.DrinkName;
import com.DrinkApp.Fun.Utils.Exceptions.NotFriendsException;
import com.DrinkApp.Fun.Utils.Exceptions.UserNotFoundException;
import com.DrinkApp.Fun.Utils.Response.PostUploadResponse;
import com.DrinkApp.Fun.Utils.Response.UserProfileResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {

    public List<PostDto> getAllPosts(UserDetails userDetails) throws UserNotFoundException;

    PostUploadResponse savePost(UserDetails userDetails, String description, DrinkName drinkName, MultipartFile image) throws IOException;

    UserProfileResponse getFeedByUser(String username) throws UserNotFoundException, NotFriendsException;
}
