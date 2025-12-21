package ma.youcode.Al.Baraka.Digital.mapper;

import ma.youcode.Al.Baraka.Digital.dto.response.AccountResponse;
import ma.youcode.Al.Baraka.Digital.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountResponse toDto(Account account);
}
