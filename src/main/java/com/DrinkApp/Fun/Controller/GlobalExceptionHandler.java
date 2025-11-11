package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Utils.Exceptions.*;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFoundException(UserNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(SameUserFriendshipException.class)
    public ResponseEntity<?> handleSameUserFriendshipException(SameUserFriendshipException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(FriendshipAlreadyRequestedException.class)
    public ResponseEntity<?> handleFriendshipAlreadyRequestedException(FriendshipAlreadyRequestedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotificationNotFoundException.class)
    public void handleNotificationNotFoundException() {

    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(FriendshipNotFoundException.class)
    public void handleFriendshipNotFoundException() {

    }

    @ExceptionHandler(UsernameAlreadyUsedException.class)
    public ResponseEntity<?> handleUsernameAlreadyUsedException(UsernameAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyUsedException.class)
    public ResponseEntity<?> handleEmailAlreadyUsedException(EmailAlreadyUsedException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsExcpetion() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email or password incorrect");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<String> handleExpiredJwt(ExpiredJwtException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("JWT expired, please log in again.");
    }

    @ExceptionHandler(NotFriendsException.class)
    public ResponseEntity<?> handleNotFriendsExcpetion(NotFriendsException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("JWT expired, please log in again.");
    }

}
