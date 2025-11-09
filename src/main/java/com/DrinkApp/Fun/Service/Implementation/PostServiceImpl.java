package com.DrinkApp.Fun.Service.Implementation;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Entity.PhotoEntity;
import com.DrinkApp.Fun.Entity.PostEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.PostMapper;
import com.DrinkApp.Fun.Repository.FriendshipRepo;
import com.DrinkApp.Fun.Repository.PhotoRepo;
import com.DrinkApp.Fun.Repository.PostRepo;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Interfaces.PostService;
import com.DrinkApp.Fun.Service.Interfaces.S3Service;
import com.DrinkApp.Fun.Utils.Enums.DrinkName;
import com.DrinkApp.Fun.Utils.Enums.Status;
import com.DrinkApp.Fun.Utils.Exceptions.CurrentUserNotFoundException;
import com.DrinkApp.Fun.Utils.Response.PostUploadResponse;
import com.DrinkApp.Fun.Utils.Response.UserProfileResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepo userRepo;
    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final S3Service s3Service;
    private final FriendshipRepo friendshipRepo;

    @Override
    public List<PostDto> getAllPosts(UserDetails userDetails) {

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<PostEntity> posts = postRepo.findAllPostsForUserAndFriends(currentUser.getId());

        return posts.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public PostUploadResponse savePost(UserDetails userDetails, String description, DrinkName drinkName, MultipartFile image) throws IOException {

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername())
                .orElseThrow(CurrentUserNotFoundException::new);

        switch (drinkName){
            case BEER -> {
                currentUser.setNumberBeers(currentUser.getNumberBeers() + 1);
                currentUser.setTotalPoints(currentUser.getTotalPoints() + 2);
            }
            case WHINE -> {
                currentUser.setNumberWhine(currentUser.getNumberWhine() + 1);
                currentUser.setTotalPoints(currentUser.getTotalPoints() + 3);
            }
            case LONG_DRINK -> {
                currentUser.setNumberLongDrink(currentUser.getNumberLongDrink() + 1);
                currentUser.setTotalPoints(currentUser.getTotalPoints() + 4);
            }
            case SHOT -> {
                currentUser.setNumberShot(currentUser.getNumberShot() + 1);
                currentUser.setTotalPoints(currentUser.getTotalPoints() + 5);
            }
            case CIGARRET -> {
                currentUser.setNumberCigarret(currentUser.getNumberCigarret() + 1);
                currentUser.setTotalPoints(currentUser.getTotalPoints() + 1);
            }
            case HEETS -> {
                currentUser.setNumberHeets(currentUser.getNumberHeets() + 1);
                currentUser.setTotalPoints(currentUser.getTotalPoints() + 1);
            }
        }
        userRepo.save(currentUser);

        String photoUrl = s3Service.uploadFile(image);

        PhotoEntity newPhoto = PhotoEntity.builder()
                .image(photoUrl)
                .createdAt(LocalDateTime.now())
                .build();

        PostEntity newPost = PostEntity.builder()
                .image(newPhoto)
                .user(currentUser)
                .createdAt(LocalDateTime.now())
                .drinkName(drinkName)
                .build();

        newPhoto.setPost(newPost);
        postRepo.save(newPost);

        return new PostUploadResponse("Post uploaded successfully");
    }

    @Override
    public UserProfileResponse getFeedByUser(String username) {
        UserEntity user = userRepo.findByUname(username).orElseThrow(() -> new RuntimeException("User not found"));

        String currentEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserEntity currentUser = userRepo.findByEmail(currentEmail)
                .orElseThrow(CurrentUserNotFoundException::new);

        if (!currentUser.getId().equals(user.getId())) {
            boolean isFriend = friendshipRepo.findByRequesterAndReceiverAndStatus(currentUser, user, Status.ACCEPTED).isPresent()
                    || friendshipRepo.findByReceiverAndRequesterAndStatus(currentUser, user, Status.ACCEPTED).isPresent();

            if (!isFriend) {
                throw new AccessDeniedException("You are not friends with this user.");
            }
        }

        List<PostDto> postsUser = postRepo.findAllPostsByUser(user).stream().map(postMapper::toDto).toList();

        return UserProfileResponse.builder()
                .id(user.getId())
                .username(user.getUname())
                .userPhoto(user.getImage())
                .totalPoints(user.getTotalPoints())
                .numberBeers(user.getNumberBeers())
                .numberWhine(user.getNumberWhine())
                .numberLongDrink(user.getNumberLongDrink())
                .numberShot(user.getNumberShot())
                .numberCigarret(user.getNumberCigarret())
                .numberHeets(user.getNumberHeets())
                .posts(postsUser)
                .build();
    }

}
