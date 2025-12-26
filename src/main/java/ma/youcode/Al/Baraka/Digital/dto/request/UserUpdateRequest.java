package ma.youcode.Al.Baraka.Digital.dto.request;

import ma.youcode.Al.Baraka.Digital.enums.UserRole;

public record UserUpdateRequest(
        String username,
        String email,
        UserRole role
) {
}
