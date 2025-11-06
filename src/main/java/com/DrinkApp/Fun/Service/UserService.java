package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Dto.UserDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.UserMapper;
import com.DrinkApp.Fun.Repository.UserRepo;
import com.DrinkApp.Fun.Utils.Response.ImageUploadResponse;
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

    public Optional<UserDto> findUserByUsername(String username){
        return userRepo.findByUname(username).map(userMapper::toDto);
    }

    public ImageUploadResponse saveProfilePicture(UserDetails userDetails, MultipartFile image) throws IOException
    {

        UserEntity currentUser = userRepo.findByEmail(userDetails.getUsername()).orElseThrow();
        currentUser.setProfilePicture(image.getBytes());
        userRepo.save(currentUser);

        return new ImageUploadResponse("Image uploaded successfully");

    }
}
