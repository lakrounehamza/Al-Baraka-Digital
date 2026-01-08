package ma.youcode.Al.Baraka.Digital.controller;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.request.UserUpdateRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class AdminController {

    private final IUserService  userService;

    @GetMapping("users")
    ResponseEntity<Page<UserResponseDto>>  getAllUser(Pageable p){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll(p));
    }

    @GetMapping("users/{id}")
    ResponseEntity<UserResponseDto>  getUser(@PathVariable UUID  id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(id));
    }

    @DeleteMapping("/users/{id}/delete")
    ResponseEntity<UserResponseDto>  delete(@PathVariable UUID  id){
        return ResponseEntity.status(HttpStatus.OK).body(userService.Delete(id));
    }
    @PatchMapping("/users/{id}/update")
    ResponseEntity<UserResponseDto>   update(@PathVariable UUID  id  , @RequestBody UserUpdateRequest  request){
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id,request));
    }


}
