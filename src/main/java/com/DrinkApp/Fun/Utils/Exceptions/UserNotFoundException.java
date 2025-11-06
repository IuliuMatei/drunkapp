package com.DrinkApp.Fun.Utils.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserNotFoundException extends RuntimeException {

    private String message;
    private String username;

    public UserNotFoundException(String username){

        super("User not found with username: " + username);

    }
}
