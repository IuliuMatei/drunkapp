package com.DrinkApp.Fun.Mapper;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Entity.PostEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "user.uname", target = "uname")
    @Mapping(source = "user.image", target = "imageProfile")
    @Mapping(target = "image", source = "image.image")
    PostDto toDto(PostEntity entity);

    @InheritInverseConfiguration
    PostEntity toEntity(PostDto dto);

}
