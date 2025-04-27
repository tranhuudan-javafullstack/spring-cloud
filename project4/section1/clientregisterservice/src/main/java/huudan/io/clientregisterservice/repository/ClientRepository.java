package huudan.io.clientregisterservice.repository;

import huudan.io.clientregisterservice.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);

    void deleteByClientId(String clientId);
}
