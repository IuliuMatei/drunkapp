package com.DrinkApp.Fun.Controller;


import com.DrinkApp.Fun.Service.Auth.AuthService;
import com.DrinkApp.Fun.Utils.Auth.AuthenticationRequest;
import com.DrinkApp.Fun.Utils.Auth.AuthenticationResponse;
import com.DrinkApp.Fun.Utils.Auth.RegisterRequest;
import com.DrinkApp.Fun.Utils.Exceptions.EmailAlreadyUsedException;
import com.DrinkApp.Fun.Utils.Exceptions.UsernameAlreadyUsedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) throws EmailAlreadyUsedException, UsernameAlreadyUsedException {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }

}
