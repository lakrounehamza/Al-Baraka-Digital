package ma.youcode.Al.Baraka.Digital.service.interfaces;

import ma.youcode.Al.Baraka.Digital.dto.request.LoginRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.request.UserRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;

import java.util.Map;

public interface IAuthService {
    UserResponseDto signup(UserRequestDto request);
    UserResponseDto signin(LoginRequestDto request);
    Map<String,String> logout();
}
