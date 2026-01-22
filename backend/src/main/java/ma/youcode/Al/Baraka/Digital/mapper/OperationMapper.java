package ma.youcode.Al.Baraka.Digital.mapper;

import ma.youcode.Al.Baraka.Digital.dto.request.OperationRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.OperationResponse;
import ma.youcode.Al.Baraka.Digital.entity.Account;
import ma.youcode.Al.Baraka.Digital.entity.Operation;
import ma.youcode.Al.Baraka.Digital.exception.NotFoundException;
import ma.youcode.Al.Baraka.Digital.repository.AccountRepository;
import ma.youcode.Al.Baraka.Digital.repository.OperationRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OperationMapper {

    OperationResponse toDto(Operation operation);

    @Mapping(
            target = "accountDestination",
            expression = "java(mapAccount(request.accountDestination_id(), accountRepository))"
    )
    Operation toEntity(
            OperationRequest request,
            @Context AccountRepository accountRepository
    );

    default Account mapAccount(String numer, AccountRepository accountRepository) {
        if(numer==null)
        return null;
        return accountRepository.findByNumer(numer).orElseThrow(() ->
                new NotFoundException("Account not found: " + numer)
        );



    }
}

