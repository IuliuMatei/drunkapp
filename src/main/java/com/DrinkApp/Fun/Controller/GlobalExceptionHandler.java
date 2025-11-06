package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Utils.Exceptions.FriendshipNotFoundException;
import com.DrinkApp.Fun.Utils.Exceptions.NotificationNotFoundException;
import com.DrinkApp.Fun.Utils.Exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public void handleUserNotFoundException() {

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotificationNotFoundException.class)
    public void handleNotificationNotFoundException() {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FriendshipNotFoundException.class)
    public void handleFriendshipNotFoundException() {

    }
}
