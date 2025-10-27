package com.DrinkApp.Fun.Mapper;

import com.DrinkApp.Fun.Dto.UserDto;
import com.DrinkApp.Fun.Entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(UserEntity user);

    UserEntity toEntity(UserDto dto);
}

