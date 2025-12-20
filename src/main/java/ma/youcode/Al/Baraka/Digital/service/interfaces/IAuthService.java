package ma.youcode.Al.Baraka.Digital.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import ma.youcode.Al.Baraka.Digital.dto.request.LoginRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.request.UserRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.response.LoginResponseDto;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;

import java.util.Map;

public interface IAuthService {
    UserResponseDto signup(UserRequestDto request);
    LoginResponseDto signin(LoginRequestDto request);
    Map<String,String> logout(HttpServletRequest request);
    UserResponseDto profil();
}
