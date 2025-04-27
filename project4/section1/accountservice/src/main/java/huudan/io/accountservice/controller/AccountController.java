package huudan.io.accountservice.controller;

import jakarta.annotation.security.PermitAll;
import huudan.io.accountservice.client.NotificationService;
import huudan.io.accountservice.client.StatisticService;
import huudan.io.accountservice.model.AccountDTO;
import huudan.io.accountservice.model.MessageDTO;
import huudan.io.accountservice.model.StatisticDTO;
import huudan.io.accountservice.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
public class AccountController {

    AccountService accountService;

    StatisticService statisticService;

    NotificationService notificationService;

    // add new
    @PostMapping("/register")
    @PermitAll
    public AccountDTO register(@RequestBody AccountDTO accountDTO, @RequestHeader("Authorization") String bearerToken) {
        accountDTO.setRoles(new HashSet<String>(List.of("ROLE_USER")));
        accountService.add(accountDTO);

        statisticService.add(new StatisticDTO("Account " + accountDTO.getUsername() + " is created", new Date()));

        // send notificaiton
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFrom("jmaster.io@gmail.com");
        messageDTO.setTo(accountDTO.getEmail());// username is email
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Welcome to JMaster.io");
        messageDTO.setContent("JMaster is online learning platform.");

        notificationService.sendNotification(messageDTO);

        return accountDTO;
    }

    @PreAuthorize("hasAuthority('SCOPE_read') && hasRole('ADMIN')")
    @PostMapping("/account")
    public AccountDTO addAccount(@RequestBody AccountDTO accountDTO, @RequestHeader("Authorization") String bearerToken) {
        accountService.add(accountDTO);

        statisticService.add(new StatisticDTO("Account " + accountDTO.getUsername() + " is created", new Date()));

        // send notificaiton
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setFrom("jmaster.io@gmail.com");
        messageDTO.setTo(accountDTO.getEmail());// username is email
        messageDTO.setToName(accountDTO.getName());
        messageDTO.setSubject("Welcome to JMaster.io");
        messageDTO.setContent("JMaster is online learning platform.");

        notificationService.sendNotification(messageDTO);

        return accountDTO;
    }

    // get all
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/accounts")
    public List<AccountDTO> getAll() {
        log.info("AccountService Controller: get all accounts");

        statisticService.add(new StatisticDTO("Get all accounts", new Date()));

        return accountService.getAll();
    }

    @PreAuthorize("hasAuthority('SCOPE_read') && isAuthenticated()")
    @PostAuthorize("returnObject.body.username == authentication.name || hasRole('ADMIN')")
    @GetMapping("/account/{id}")
    public ResponseEntity<AccountDTO> get(@PathVariable(name = "id") Long id) {
        return Optional.of(new ResponseEntity<AccountDTO>(accountService.getOne(id), HttpStatus.OK))
                .orElse(new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND));
    }

    @PreAuthorize("hasAuthority('SCOPE_write') && hasRole('ADMIN')")
    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable(name = "id") Long id) {
        statisticService.add(new StatisticDTO("Delete account id " + id, new Date()));

        accountService.delete(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_write') && hasRole('ADMIN')")
    @PutMapping("/account")
    public void update(@RequestBody AccountDTO accountDTO) {
        statisticService.add(new StatisticDTO("Update account: " + accountDTO.getUsername(), new Date()));

        accountService.update(accountDTO);
    }

    //    @PreAuthorize("#oauth2.hasScope('read') && isAuthenticated()")
    @GetMapping("/me")
    public Principal me(Principal principal, @RequestHeader("Authorization") String bearerToken) {
        return principal;
    }
}
