package huudan.io.clientregisterservice.controller;

import huudan.io.clientregisterservice.entity.Client;
import huudan.io.clientregisterservice.repository.ClientRepository;
import jakarta.annotation.security.PermitAll;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("#oauth2.hasScope('write') && hasRole('ADMIN')")
@PermitAll
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {
    ClientRepository clientRepository;

    @PostMapping("/clients")
    public Client addAccount(@RequestBody Client client) {
        client.setClientSecret(new BCryptPasswordEncoder().encode(client.getClientSecret()));
        clientRepository.save(client);
        return client;
    }

    // get all
    @GetMapping("/clients")
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @DeleteMapping("/clients")
    public void delete(@RequestParam(name = "clientId") String clientId) {
        clientRepository.deleteByClientId(clientId);
    }
}
