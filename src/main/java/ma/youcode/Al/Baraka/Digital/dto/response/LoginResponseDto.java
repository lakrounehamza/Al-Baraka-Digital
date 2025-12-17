package ma.youcode.Al.Baraka.Digital.dto.response;

public record LoginResponseDto(
        UserResponseDto user,
        String token
) {
}
