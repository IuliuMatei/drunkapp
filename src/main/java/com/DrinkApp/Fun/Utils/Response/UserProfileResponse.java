package com.DrinkApp.Fun.Utils.Response;

import com.DrinkApp.Fun.Dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponse {

    private Long id;
    private String username;
    private String userPhoto;
    private Integer totalPoints;
    private Integer numberBeers;
    private Integer numberWhine;
    private Integer numberLongDrink;
    private Integer numberShot;
    private Integer numberCigarret;
    private Integer numberHeets;
    private List<PostDto> posts;

}
