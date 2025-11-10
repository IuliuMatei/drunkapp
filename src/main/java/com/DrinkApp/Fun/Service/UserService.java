package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Dto.UserDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.UserMapper;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Service.Implementation.S3ServiceImpl;
import com.DrinkApp.Fun.Utils.Exceptions.CurrentUserNotFoundException;
import com.DrinkApp.Fun.Utils.Response.ImageUploadResponse;
import com.DrinkApp.Fun.Utils.Response.UserProfilePictureResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final S3ServiceImpl s3Service;

    public Optional<UserDto> findUserByUsername(String username){
        return userRepo.findByUname(username).map(userMapper::toDto);
    }

    public ImageUploadResponse saveProfilePicture(UserDetails userDetails, MultipartFile image) throws IOException
    {

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername()).orElseThrow();
        String photoUrl = s3Service.uploadFile(image);
        currentUser.setImage(photoUrl);
        userRepo.save(currentUser);

        return new ImageUploadResponse("Image uploaded successfully");

    }

    public UserProfilePictureResponse findUsernameAndImage(UserDetails userDetails) {

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername()).orElseThrow(CurrentUserNotFoundException::new);

        return UserProfilePictureResponse.builder()
                .uname(currentUser.getUname())
                .image(currentUser.getImage())
                .build();

    }
}
