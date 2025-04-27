package huudan.io.accountservice.service;

import huudan.io.accountservice.model.MessageDTO;
import huudan.io.accountservice.model.StatisticDTO;
import huudan.io.accountservice.repo.MessageRepo;
import huudan.io.accountservice.repo.StatisticRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PollingService {
    KafkaTemplate<String, Object> kafkaTemplate;
    MessageRepo messageRepo;
    StatisticRepo statisticRepo;


    @Scheduled(fixedDelay = 1000)
    public void producer() {
        List<MessageDTO> messageDTOs = messageRepo.findByStatus(false);

        for (MessageDTO messageDTO : messageDTOs) {
            kafkaTemplate.send("notification", messageDTO).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            log.info("Sent message=[" + messageDTO.getId() +
                                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            messageDTO.setStatus(true);// success
                            messageRepo.save(messageDTO);
                        } else {
                            log.error("Unable to send message=[" +
                                    messageDTO.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );
//            .addCallback(new KafkaSendCallback<String, Object>() {
//                @Override
//                public void onFailure(KafkaProducerException ex) {
//                    // handle fail, save db event failed
//                    log.error("FAIL ", ex);
//                }
//
//                @Override
//                public void onSuccess(SendResult<String, Object> result) {
//                    // handle success
//                    log.error("SUCCESS ");
//
//                    messageDTO.setStatus(true);// success
//                    messageRepo.save(messageDTO);
//                }
//            });
        }

        List<StatisticDTO> statisticDTOs = statisticRepo.findByStatus(false);
        for (StatisticDTO statisticDTO : statisticDTOs) {
            kafkaTemplate.send("statistic", statisticDTO).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            log.info("Sent message=[" + statisticDTO.getId() +
                                    "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            statisticDTO.setStatus(true);// success
                            statisticRepo.save(statisticDTO);
                        } else {
                            log.error("Unable to send message=[" +
                                    statisticDTO.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );
        }
    }

    @Scheduled(fixedDelay = 60000)
    public void delete() {
        List<MessageDTO> messageDTOs = messageRepo.findByStatus(true);
        messageRepo.deleteAllInBatch(messageDTOs);

        List<StatisticDTO> statisticDTOs = statisticRepo.findByStatus(true);
        statisticRepo.deleteAllInBatch(statisticDTOs);
    }
}
