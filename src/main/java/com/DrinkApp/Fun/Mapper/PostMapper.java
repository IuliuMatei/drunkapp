package com.DrinkApp.Fun.Mapper;

import com.DrinkApp.Fun.Dto.PostDto;
import com.DrinkApp.Fun.Entity.PostEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {

    PostDto toDto(PostEntity entity);

    PostEntity toEntity(PostDto dto);

}
