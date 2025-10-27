package com.DrinkApp.Fun.Service;

import com.DrinkApp.Fun.Dto.UserDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Mapper.UserMapper;
import com.DrinkApp.Fun.Repository.UserRepo;

import java.util.Optional;

public class UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;

    public UserService(UserRepo userRepo, UserMapper userMapper){
        this.userRepo = userRepo;
        this.userMapper = userMapper;
    }

    public Optional<UserDto> findUserByUsername(String username){
        return userRepo.findByUsername(username).map(userMapper::toDto);
    }
}
