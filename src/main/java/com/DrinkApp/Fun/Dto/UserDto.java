package com.DrinkApp.Fun.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    private String uname;
    private String image;
    private Integer totalNumberPoints;
    private Integer numberBeers;
    private Integer numberWhine;
    private Integer numberLongDrink;
    private Integer numberShot;
    private Integer numberCigarret;
    private Integer numberHeets;
}
