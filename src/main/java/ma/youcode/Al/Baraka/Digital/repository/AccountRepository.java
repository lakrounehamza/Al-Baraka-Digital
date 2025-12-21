package ma.youcode.Al.Baraka.Digital.repository;

import ma.youcode.Al.Baraka.Digital.entity.Account;
import ma.youcode.Al.Baraka.Digital.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account , UUID> {
    @Query("select c  from  Account c  where c.onwer.username =:onwer ")
    Account findByOnwer(String onwer);
}
