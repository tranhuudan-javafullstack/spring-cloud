package huudan.io.accountservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import huudan.io.accountservice.entity.Account;
import huudan.io.accountservice.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import huudan.io.accountservice.model.AccountDTO;

public interface AccountService {
    void add(AccountDTO accountDTO);

    void update(AccountDTO accountDTO);

    void updatePassword(AccountDTO accountDTO);

    void delete(Long id);

    List<AccountDTO> getAll();

    AccountDTO getOne(Long id);

    AccountDTO findByUsername(String username);
}

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@Service
@Slf4j
class AccountServiceImpl implements AccountService {
    AccountRepository accountRepository;

    ModelMapper modelMapper;

    @Override
    public void add(AccountDTO accountDTO) {
        Account account = modelMapper.map(accountDTO, Account.class);
        account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));

        accountRepository.save(account);

        accountDTO.setId(account.getId());
    }

    @Override
    public void update(AccountDTO accountDTO) {
        Optional<Account> byId = accountRepository.findById(accountDTO.getId());
        byId.ifPresent(account -> {
            modelMapper.typeMap(AccountDTO.class, Account.class)
                    .addMappings(mapper -> mapper.skip(Account::setPassword)).map(accountDTO, account);
            accountRepository.save(account);
        });
    }

    @Override
    public void updatePassword(AccountDTO accountDTO) {
        Optional<Account> byId = accountRepository.findById(accountDTO.getId());
        byId.ifPresent(account -> {
            account.setPassword(new BCryptPasswordEncoder().encode(accountDTO.getPassword()));
            accountRepository.save(account);
        });
    }

    @Override
    public void delete(Long id) {
        Optional<Account> byId = accountRepository.findById(id);
        byId.ifPresent(accountRepository::delete);
    }

    @Override
//    @Async
    public List<AccountDTO> getAll() {
        log.info("Get All in AccountService");
        List<AccountDTO> accountDTOs = new ArrayList<>();

        accountRepository.findAll().forEach((account) -> {
            accountDTOs.add(modelMapper.map(account, AccountDTO.class));
        });

        return accountDTOs;
    }

    @Override
    public AccountDTO getOne(Long id) {
        Optional<Account> byId = accountRepository.findById(id);
        return byId.map(account -> modelMapper.map(account, AccountDTO.class)).orElseGet(null);

    }

    @Override
    public AccountDTO findByUsername(String username) {
        Account account = accountRepository.findByUsername(username);

        if (account != null)
            return modelMapper.map(account, AccountDTO.class);

        return null;
    }
}