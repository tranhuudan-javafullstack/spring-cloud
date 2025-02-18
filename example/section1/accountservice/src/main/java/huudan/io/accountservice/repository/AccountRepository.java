package huudan.io.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import huudan.io.accountservice.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);
}
