package ma.youcode.Al.Baraka.Digital.service.interfaces;

import ma.youcode.Al.Baraka.Digital.dto.response.AccountResponse;
import ma.youcode.Al.Baraka.Digital.entity.Account;

public interface IAccountService {
    AccountResponse save(Account account);
    AccountResponse getAccountconucted();
}
