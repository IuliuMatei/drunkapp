package com.DrinkApp.Fun.Controller;

import com.DrinkApp.Fun.Dto.UserDto;
import com.DrinkApp.Fun.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username){
        Optional<UserDto> userDtoOptional = userService.findUserByUsername(username);
        return userDtoOptional.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
