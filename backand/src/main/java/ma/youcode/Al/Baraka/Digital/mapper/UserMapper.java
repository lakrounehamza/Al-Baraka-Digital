package ma.youcode.Al.Baraka.Digital.mapper;


import ma.youcode.Al.Baraka.Digital.dto.request.UserRequestDto;
import ma.youcode.Al.Baraka.Digital.dto.response.UserResponseDto;
import ma.youcode.Al.Baraka.Digital.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDto request);
    UserResponseDto toDto(User  user);
}
