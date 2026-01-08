package ma.youcode.Al.Baraka.Digital.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.request.LoginRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.request.UserRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.response.LoginResponseDto;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> signup(@RequestBody UserRequestDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.signup(request));
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto>  sigin(@RequestBody LoginRequestDto  request){
        return ResponseEntity.status(HttpStatus.OK).body(authService.signin(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<Map>  logout(HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.OK).body(authService.logout(request));
    }


    @GetMapping("/profile")
    public ResponseEntity<UserResponseDto>  pofile(){
        return ResponseEntity.status(HttpStatus.OK).body(authService.profil());
    }

}
