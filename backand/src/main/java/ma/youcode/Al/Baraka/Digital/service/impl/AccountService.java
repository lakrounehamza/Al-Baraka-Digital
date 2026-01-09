package ma.youcode.Al.Baraka.Digital.service.impl;

import lombok.AllArgsConstructor;
import ma.youcode.Al.Baraka.Digital.dto.response.AccountResponse;
import ma.youcode.Al.Baraka.Digital.entity.Account;
import ma.youcode.Al.Baraka.Digital.mapper.AccountMapper;
import ma.youcode.Al.Baraka.Digital.repository.AccountRepository;
import ma.youcode.Al.Baraka.Digital.service.interfaces.IAccountService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService implements IAccountService {

    private AccountMapper accountMapper;
    private AccountRepository accountRepository;

    @Override
    public AccountResponse save(Account account) {
        return accountMapper.toDto(accountRepository.save(account));
    }

    @Override
    public AccountResponse getAccountconucted() {
        String  name  = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByOnwer(name);
        return accountMapper.toDto(account);
    }

}
