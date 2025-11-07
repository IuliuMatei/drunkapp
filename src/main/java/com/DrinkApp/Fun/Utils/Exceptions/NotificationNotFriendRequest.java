package com.DrinkApp.Fun.Utils.Exceptions;

import lombok.Data;

@Data
public class NotificationNotFriendRequest extends RuntimeException{

    public NotificationNotFriendRequest(){
        super("Not friend request");
    }
}
