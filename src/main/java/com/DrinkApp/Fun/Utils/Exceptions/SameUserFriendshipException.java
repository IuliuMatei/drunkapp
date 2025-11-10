package com.DrinkApp.Fun.Utils.Exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class SameUserFriendshipException extends Exception{

    public SameUserFriendshipException() {

        super("Can not send friend request to you");

    }

}
