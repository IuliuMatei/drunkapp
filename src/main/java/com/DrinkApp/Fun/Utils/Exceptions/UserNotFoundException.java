package com.DrinkApp.Fun.Utils.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserNotFoundException extends Exception {

    public UserNotFoundException(){

        super("User not found");

    }
}
