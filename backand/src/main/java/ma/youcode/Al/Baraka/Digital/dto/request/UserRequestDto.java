package ma.youcode.Al.Baraka.Digital.dto.request;


import ma.youcode.Al.Baraka.Digital.enums.UserRole;

public record UserRequestDto (
         String username,
         String email,
         UserRole role,
         String password
){
}
