package huudan.io.accountservice.controller;

import huudan.io.accountservice.model.AccountDTO;
import huudan.io.accountservice.model.MessageDTO;
import huudan.io.accountservice.model.StatisticDTO;
import huudan.io.accountservice.repo.AccountRepo;
import huudan.io.accountservice.repo.MessageRepo;
import huudan.io.accountservice.repo.StatisticRepo;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/account")
public class AccountController {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final AccountRepo accountRepo;

    private final MessageRepo messageRepo;

    private final StatisticRepo statisticRepo;

    public AccountController(KafkaTemplate<String, Object> kafkaTemplate, AccountRepo accountRepo, MessageRepo messageRepo, StatisticRepo statisticRepo) {
        this.kafkaTemplate = kafkaTemplate;
        this.accountRepo = accountRepo;
        this.messageRepo = messageRepo;
        this.statisticRepo = statisticRepo;
    }

    @PostMapping("/new")
    public AccountDTO create(@RequestBody AccountDTO account) {
        StatisticDTO stat = new StatisticDTO("Account " + account.getEmail() + " is created", new Date());
        stat.setStatus(false);

        // send notificaiton
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTo(account.getEmail());
        messageDTO.setToName(account.getName());
        messageDTO.setSubject("Welcome to JMaster.io");
        messageDTO.setContent("JMaster is online learning platform.");
        messageDTO.setStatus(false);

        accountRepo.save(account);
        messageRepo.save(messageDTO);
        statisticRepo.save(stat);

        for (int i = 0; i < 100; i++)
            kafkaTemplate.send("notification", messageDTO).whenComplete(
                    (result, ex) -> {
                        if (ex == null) {
                            System.out.println("Sent message=[" + messageDTO.getId() +
                                               "] with offset=[" + result.getRecordMetadata().offset() + "]");
                            messageDTO.setStatus(true);// success
                            messageRepo.save(messageDTO);
                        } else {
                            System.out.println("Unable to send message=[" +
                                               messageDTO.getId() + "] due to : " + ex.getMessage());
                        }
                    }
            );
//        .addCallback(new KafkaSendCallback<String, Object>() {
//				@Override
//				public void onFailure(KafkaProducerException ex) {
//					// handle fail, save db event failed
//					ex.printStackTrace();
//				}
//				@Override
//				public void onSuccess(SendResult<String, Object> result) {
//					// handle success
//					System.out.println(result.getRecordMetadata().partition());
//				}
//			});

        kafkaTemplate.send("statistic", stat);

        return account;
    }
}
