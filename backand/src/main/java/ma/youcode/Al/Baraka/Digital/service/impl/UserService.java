package ma.youcode.Al.Baraka.Digital.service.impl;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.request.UserUpdateRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;
import ma.youcode.Al.Baraka.Digital.entity.User;
import ma.youcode.Al.Baraka.Digital.enums.UserRole;
import ma.youcode.Al.Baraka.Digital.exception.NotFoundException;
import ma.youcode.Al.Baraka.Digital.mapper.UserMapper;
import ma.youcode.Al.Baraka.Digital.repository.UserRepository;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper  userMapper;

    @Override
    public Page<UserResponseDto> getAll(Pageable pageable) {
        return  userRepository.findAll(pageable).map(userMapper::toDto);
    }

    @Override
    public UserResponseDto Delete(UUID id) {
       User  user= userRepository.findById(id).orElseThrow(()-> new NotFoundException("not found user"));
        user.setActive(false);
         User  userSaved  = userRepository.save(user);
        return  userMapper.toDto(userSaved);
    }

    @Override
    public UserResponseDto getUser(UUID id) {
        User  user= userRepository.findById(id).orElseThrow(()-> new NotFoundException("not found user"));
        return  userMapper.toDto(user);
    }

    @Override
    public UserResponseDto update(UUID id, UserUpdateRequest request) {
        User  user= userRepository.findById(id).orElseThrow(()-> new NotFoundException("not found user"));
//        Optional.ofNullable(request.active())
//                .ifPresent(user::setActive);
        if(request.email() != null && !request.email().isEmpty())
            user.setEmail(request.email());
        if(request.username() != null && !request.username().isEmpty())
            user.setUsername(request.username());
        if((request.role() != null) && (request.role().equals(UserRole.CLIENT) || request.role().equals(UserRole.AGENT_BANCAIRE)))
            user.setRole(request.role());
        User userSaved = userRepository.save(user);
        return userMapper.toDto(userSaved);
    }
}
