package com.DrinkApp.Fun.Utils.Exceptions;

public class CurrentUserNotFoundException extends RuntimeException{

    public CurrentUserNotFoundException() {
        super("Current user not found");
    }
}
