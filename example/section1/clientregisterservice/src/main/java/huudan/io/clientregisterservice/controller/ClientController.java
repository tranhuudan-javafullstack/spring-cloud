package huudan.io.clientregisterservice.controller;

import jakarta.annotation.security.PermitAll;
import huudan.io.clientregisterservice.entity.Client;
import huudan.io.clientregisterservice.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@PreAuthorize("#oauth2.hasScope('write') && hasRole('ADMIN')")
@PermitAll
@RestController
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

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
