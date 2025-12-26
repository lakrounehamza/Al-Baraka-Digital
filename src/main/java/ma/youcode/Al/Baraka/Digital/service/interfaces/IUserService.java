package ma.youcode.Al.Baraka.Digital.service.interfaces;

import ma.youcode.Al.Baraka.Digital.dto.request.UserUpdateRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface IUserService {
    Page<UserResponseDto>   getAll(Pageable pageable);
    UserResponseDto  Delete (UUID  id);
    UserResponseDto  getUser(UUID  id);
    UserResponseDto  update(UUID  id , UserUpdateRequest request);
}
