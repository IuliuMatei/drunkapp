package com.DrinkApp.Fun.Utils.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class NotificationNotFoundException extends RuntimeException{

    public NotificationNotFoundException(){

        super("Notification not found");

    }

}
