package huudan.io.accountservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import huudan.io.accountservice.model.AccountDTO;

public interface AccountRepo extends JpaRepository<AccountDTO, Integer> {

}
