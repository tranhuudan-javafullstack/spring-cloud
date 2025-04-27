package huudan.io.accountservice.client;

import huudan.io.accountservice.model.MessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings("deprecation")
@FeignClient(name = "notification-service", fallback = NotificationServiceImpl.class, configuration = NotificationFeignClientConfiguration.class)
public interface NotificationService {

    @PostMapping("/send-notification")
    void sendNotification(@RequestBody MessageDTO messageDTO);
//    public void sendNotification(@RequestBody MessageDTO messageDTO, @RequestHeader("Authorization") String bearerToken);
}

@Component
@Slf4j
class NotificationServiceImpl implements NotificationService {

    @Override
    public void sendNotification(MessageDTO messageDTO) {
        // fallback
        log.error("Notification Service is slow");
    }
}

@SuppressWarnings("deprecation")
class NotificationFeignClientConfiguration {

}