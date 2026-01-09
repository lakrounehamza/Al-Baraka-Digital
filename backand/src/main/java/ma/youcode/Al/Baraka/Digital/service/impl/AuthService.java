package ma.youcode.Al.Baraka.Digital.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.request.LoginRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.request.UserRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.response.LoginResponseDto;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;
import ma.youcode.Al.Baraka.Digital.entity.Account;
import ma.youcode.Al.Baraka.Digital.entity.User;
import ma.youcode.Al.Baraka.Digital.exception.DuplicateUserException;
import ma.youcode.Al.Baraka.Digital.exception.NotFoundException;
import ma.youcode.Al.Baraka.Digital.mapper.UserMapper;
import ma.youcode.Al.Baraka.Digital.repository.AccountRepository;
import ma.youcode.Al.Baraka.Digital.repository.UserRepository;
import ma.youcode.Al.Baraka.Digital.security.JwtUtil;
import ma.youcode.Al.Baraka.Digital.security.TokenBlacklistService;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IAuthService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;
    private final AccountRepository accountRepository;
    private AccountNumerManagment accountNumerManagment;

    @Override
    public UserResponseDto signup(UserRequestDto requset) {
        User user = userMapper.toEntity(requset);
        if (userRepository.findByUsername(user.getUsername()).isPresent())
            throw new DuplicateUserException("user deja existe");
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        User userSave = userRepository.save(user);
        Account account = new Account();
        account.setOnwer(userSave);
        account.setNumer(accountNumerManagment.geneted());
        accountRepository.save(account);
        return userMapper.toDto(userSave);


    }

    @Override
    public LoginResponseDto signin(LoginRequestDto request) {
        Optional<User> user = userRepository.findByUsername(request.username());
        if (!user.isPresent())
            throw new NotFoundException(" username  incoricte");
        User user1 = user.get();
        if (!BCrypt.checkpw(request.password(), user1.getPassword()))
            throw new NotFoundException(" username  incoricte");
        UserResponseDto userResponse = userMapper.toDto(user1);
        String token = jwtUtil.generateToken(user1);
        return new LoginResponseDto(userResponse, token);
    }

    @Override
    public Map<String, String> logout(HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            tokenBlacklistService.blacklistToken(token);
        }
        return Map.of("se", "ok");
    }

    @Override
    public UserResponseDto profil() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent())
            return null;
        return userMapper.toDto(user.get());
    }
}
