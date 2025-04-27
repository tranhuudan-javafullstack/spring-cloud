package huudan.io.statisticservice.service;

import huudan.io.statisticservice.entity.Statistic;
import huudan.io.statisticservice.repository.StatisticRepo;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticService {

    StatisticRepo statisticRepo;


    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        log.info("Received: " + statistic.getMessage());
        statisticRepo.save(statistic);
//		throw new RuntimeException("failed");
    }

    @KafkaListener(id = "dltGroup3", topics = "statistic.DLT")
    public void dltListen(String in) {
        log.info("Received from DLT: ");
        System.out.println(in);
    }
}
