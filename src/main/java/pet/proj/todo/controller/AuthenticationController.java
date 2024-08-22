package pet.proj.todo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pet.proj.todo.dto.user.UserLoginRequestDto;
import pet.proj.todo.dto.user.UserLoginResponseDto;
import pet.proj.todo.dto.user.UserRegistrationRequestDto;
import pet.proj.todo.dto.user.UserResponseDto;
import pet.proj.todo.exception.RegistrationException;
import pet.proj.todo.security.AuthenticationService;
import pet.proj.todo.service.UserService;

@RequiredArgsConstructor
@Tag(name = "Authentication controller",
        description = "Here we have endpoints for login and register")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @Operation(summary = "Login",
            description = "login by email and password")
    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody @Valid UserLoginRequestDto request) {
        return authenticationService.authenticate(request);
    }

    @PostMapping("/register")
    @Operation(summary = "Register",
            description = "Register new user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserResponseDto register(@RequestBody @Valid UserRegistrationRequestDto request)
            throws RegistrationException {
        return userService.register(request);
    }
}
