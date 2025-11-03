package com.DrinkApp.Fun.Dto;

import com.DrinkApp.Fun.Entity.PhotoEntity;
import com.DrinkApp.Fun.Entity.PostCommentEntity;
import com.DrinkApp.Fun.Entity.UserEntity;
import com.DrinkApp.Fun.Utils.Enums.PostType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;

    private UserEntity user;
    private PostType postType;
    private String description;
    private LocalDateTime createdAt;
    private String imageUrl;
    private List<PostCommentEntity> comments;
    private Double targetAmount;
    private Double currentAmount = 0.0;
    private String donationLink;
}
