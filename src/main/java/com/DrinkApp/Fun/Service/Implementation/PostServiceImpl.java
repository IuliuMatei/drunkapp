package com.DrinkApp.Fun.Service.Implementation;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Entity.FriendshipEntity;
import com.DrinkApp.Fun.Entity.PostEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.PostMapper;
import com.DrinkApp.Fun.Repository.FriendshipRepo;
import com.DrinkApp.Fun.Repository.PostRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Interfaces.PostService;
import com.DrinkApp.Fun.Utils.Enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

}
