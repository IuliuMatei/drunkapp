package com.DrinkApp.Fun.Utils.Exceptions;

public class UsernameAlreadyUsedException extends Exception {

    public UsernameAlreadyUsedException(){
        super("Username taken");
    }

}
