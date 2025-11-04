package com.DrinkApp.Fun.Service.Implementation;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Entity.PhotoEntity;
import com.DrinkApp.Fun.Entity.PostEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.PostMapper;
import com.DrinkApp.Fun.Repository.PostRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Interfaces.PostService;
import com.DrinkApp.Fun.Utils.Enums.DrinkName;
import com.DrinkApp.Fun.Utils.UserProfileResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final PostMapper postMapper;

    @Override
    public List<PostDto> getAllPosts(UserDetails userDetails) {

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PostEntity> posts = postRepo.findAllPostsForUserAndFriends(currentUser.getId());

        return posts.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean savePost(UserDetails userDetails, String description, DrinkName drinkName, MultipartFile image) throws IOException {

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        int points = getPoints(drinkName);
        currentUser.setTotalPoints(currentUser.getTotalPoints() + points);
        userRepo.save(currentUser);

        PhotoEntity newPhoto = PhotoEntity.builder()
                .imageArray(image.getBytes())
                .createdAt(LocalDateTime.now())
                .build();

        PostEntity newPost = PostEntity.builder()
                .photo(newPhoto)
                .user(currentUser)
                .createdAt(LocalDateTime.now())
                .drinkName(drinkName)
                .build();

        postRepo.save(newPost);
        return false;
    }

    @Override
    public UserProfileResponse getFeedByUser(String username) {
        UserEntity user = userRepo.findByUname(username).orElseThrow(() -> new RuntimeException("User not found"));

        List<PostDto> postsUser = postRepo.findAllPostsByUser(user).stream().map(postMapper::toDto).toList();

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUname())
                .userPhoto(user.getProfilePicture())
                .totalPoints(user.getTotalPoints())
                .posts(postsUser)
                .build();
    }

    private int getPoints(DrinkName drinkName){

        return switch(drinkName) {
            case BEER -> 2;
            case WHINE -> 3;
            case LONG_DRINK -> 4;
            case SHOT -> 5;
            case CIGARRET -> 1;
            case HEETS -> 0;
        };
    }

}
