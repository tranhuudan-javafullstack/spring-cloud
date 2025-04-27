package huudan.io.notificationservice.service;

import huudan.io.notificationservice.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageService {

//    @Autowired
//    private EmailService emailService;

    @KafkaListener(id = "notificationGroup", topics = "notification")
    public void listen(MessageDTO messageDTO) {
        log.info("Received: ", messageDTO.getTo());
//        emailService.sendEmail(messageDTO);
    }
}
