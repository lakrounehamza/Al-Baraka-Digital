package ma.youcode.Al.Baraka.Digital.service.impl;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.request.LoginRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.request.UserRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;
import ma.youcode.Al.Baraka.Digital.entity.User;
import ma.youcode.Al.Baraka.Digital.exception.DuplicateUserException;
import ma.youcode.Al.Baraka.Digital.mapper.UserMapper;
import ma.youcode.Al.Baraka.Digital.repository.UserRepository;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IAuthService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto signup(UserRequestDto requset) {
        User user = userMapper.toEntity(requset);
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new DuplicateUserException("user deja existe");
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        return userMapper.toDto(userRepository.save(user));


    }

    @Override
    public UserResponseDto signin(LoginRequestDto request) {
        return null;
    }

    @Override
    public Map<String, String> logout() {
        return null;
    }
}
