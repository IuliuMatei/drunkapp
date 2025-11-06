package com.DrinkApp.Fun.Utils.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipNotFoundException extends RuntimeException{

    private String message;

}
