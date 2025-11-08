package com.DrinkApp.Fun.Dto;

import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Utils.Enums.DrinkName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;

    private UserEntity user;
    private String description;
    private LocalDateTime createdAt;
    private String image;
    private DrinkName drinkName;
}
