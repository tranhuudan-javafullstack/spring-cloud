package huudan.io.statisticservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import huudan.io.statisticservice.entity.Statistic;
import huudan.io.statisticservice.repository.StatisticRepo;

@Service
public class StatisticService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StatisticRepo statisticRepo;

    public StatisticService(StatisticRepo statisticRepo) {
        this.statisticRepo = statisticRepo;
    }

    @KafkaListener(id = "statisticGroup", topics = "statistic")
    public void listen(Statistic statistic) {
        logger.info("Received: " + statistic.getMessage());
        statisticRepo.save(statistic);
//		throw new RuntimeException("failed");
    }

    @KafkaListener(id = "dltGroup3", topics = "statistic.DLT")
    public void dltListen(String in) {
        logger.info("Received from DLT: ");
        System.out.println(in);
    }
}
