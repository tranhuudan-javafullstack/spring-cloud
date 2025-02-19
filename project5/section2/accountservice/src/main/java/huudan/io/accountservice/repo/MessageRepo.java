package huudan.io.accountservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import huudan.io.accountservice.model.MessageDTO;

public interface MessageRepo extends JpaRepository<MessageDTO, Integer> {

    List<MessageDTO> findByStatus(boolean status);
}
