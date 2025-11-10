package com.DrinkApp.Fun.Utils.Exceptions;

public class EmailAlreadyUsedException extends Exception {

    public EmailAlreadyUsedException(){
        super("Account already created with this email");
    }

}
