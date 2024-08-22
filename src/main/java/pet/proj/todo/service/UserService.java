package pet.proj.todo.service;

import pet.proj.todo.dto.user.UserRegistrationRequestDto;
import pet.proj.todo.dto.user.UserResponseDto;
import pet.proj.todo.exception.RegistrationException;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException;
}
