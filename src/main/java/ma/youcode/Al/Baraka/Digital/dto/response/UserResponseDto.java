package ma.youcode.Al.Baraka.Digital.dto.response;

import ma.youcode.Al.Baraka.Digital.enums.UserRole;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDto (
         UUID id,
         String username,
         String email,
         UserRole role,
         boolean active,
         LocalDate created_at
){
}
