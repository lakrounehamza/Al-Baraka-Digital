package ma.youcode.Al.Baraka.Digital.service.impl;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.request.OperationRequest;
import ma.youcode.Al.Baraka.Digital.dto.response.OperationResponse;
import ma.youcode.Al.Baraka.Digital.entity.Account;
import ma.youcode.Al.Baraka.Digital.entity.Operation;
import ma.youcode.Al.Baraka.Digital.entity.User;
import ma.youcode.Al.Baraka.Digital.enums.OperationStatus;
import ma.youcode.Al.Baraka.Digital.enums.OperationType;
import ma.youcode.Al.Baraka.Digital.exception.InvalideInputException;
import ma.youcode.Al.Baraka.Digital.exception.NotFoundException;
import ma.youcode.Al.Baraka.Digital.mapper.OperationMapper;
import ma.youcode.Al.Baraka.Digital.repository.AccountRepository;
import ma.youcode.Al.Baraka.Digital.repository.OperationRepository;
import ma.youcode.Al.Baraka.Digital.repository.UserRepository;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class OperationService implements IOperationService {

    private final OperationRepository operationRepository;
    private final OperationMapper operationMapper;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;


    @Override
    public OperationResponse save(OperationRequest request) {
        Operation operation = operationMapper.toEntity(request, accountRepository);
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account accountClinet = accountRepository.findByOnwer(username);
        operation.setAccountSource(accountClinet);
        if (operation.getAmount().compareTo(BigDecimal.valueOf(10000)) <= 0) {
            if (operation.getType().equals(OperationType.DEPOT) == true)
                accountClinet.setBalance(accountClinet.getBalance().add(operation.getAmount()));
            else if (operation.getAmount().compareTo(accountClinet.getBalance()) > 0)
                throw new InvalideInputException("Fonds insuffisants pour le retrait");
            if (operation.getType().equals(OperationType.RETRAIT) == true)
                accountClinet.setBalance(accountClinet.getBalance().subtract(operation.getAmount()));
            if (operation.getType().equals(OperationType.VIREMENT) == true) {
                accountClinet.setBalance(accountClinet.getBalance().subtract(operation.getAmount()));
                Account accountDistenation = accountRepository.findByNumer(operation.getAccountDestination().getNumer()).orElseThrow(() -> new NotFoundException("not fond account destination"));
                accountDistenation.setBalance(accountDistenation.getBalance().add(operation.getAmount()));
                accountRepository.save(accountDistenation);
            }
            operation.setStatus(OperationStatus.CANCELED);
            accountRepository.save(accountClinet);

        }

        Operation operationSaved = operationRepository.save(operation);
        return operationMapper.toDto(operationSaved);
    }


    @Override
    public Page<OperationResponse> getAll(Pageable pageable) {
        String usename = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByOnwer(usename);
        Page<Operation> operations = operationRepository.findByAccountSource_Id(account.getId(),pageable);
        return operations.map(operationMapper::toDto);
    }

    @Override
    public Page<OperationResponse> getOperationByStatus(OperationStatus status, Pageable pageable) {
        return  operationRepository.findByStatus(status,pageable).map(operationMapper::toDto);
    }
}
