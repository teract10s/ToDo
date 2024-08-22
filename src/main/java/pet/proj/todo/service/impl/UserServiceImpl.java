package pet.proj.todo.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pet.proj.todo.dto.user.UserRegistrationRequestDto;
import pet.proj.todo.dto.user.UserResponseDto;
import pet.proj.todo.exception.RegistrationException;
import pet.proj.todo.mapper.UserMapper;
import pet.proj.todo.model.Role;
import pet.proj.todo.model.User;
import pet.proj.todo.repository.RoleRepository;
import pet.proj.todo.repository.UserRepository;
import pet.proj.todo.service.UserService;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder encoder;
    private final RoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RegistrationException("Unable to complete registration");
        }
        Role defaultRole = roleRepository.getRoleByName(Role.RoleName.USER);
        User user = userMapper.toUser(request);
        user.setRoles(Set.of(defaultRole));
        user.setPassword(encoder.encode(request.password()));
        return userMapper.toDto(userRepository.save(user));
    }
}
