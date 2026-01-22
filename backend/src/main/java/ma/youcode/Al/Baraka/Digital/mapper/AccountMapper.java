package ma.youcode.Al.Baraka.Digital.mapper;

import ma.youcode.Al.Baraka.Digital.dto.response.AccountDestinationResponse;
import ma.youcode.Al.Baraka.Digital.dto.response.AccountResponse;
import ma.youcode.Al.Baraka.Digital.entity.Account;
import ma.youcode.Al.Baraka.Digital.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    @Mapping(target = "operations",source = "operationsSource")
    AccountResponse toDto(Account account);

    @Mapping(target = "numer", expression = "java(account.getNumer())")
    @Mapping(target = "userName", expression = "java(account.getOnwer().getUsername())")
    AccountDestinationResponse toAccountDesResponse(Account account);

}
