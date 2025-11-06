package com.DrinkApp.Fun.Utils.Exceptions;

import lombok.Data;

@Data
public class FriendshipAlreadyRequestedException extends RuntimeException{

    public FriendshipAlreadyRequestedException(){

        super("Friend request already sent");

    }

}
