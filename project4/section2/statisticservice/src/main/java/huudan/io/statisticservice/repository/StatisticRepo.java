package huudan.io.statisticservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import huudan.io.statisticservice.entity.Statistic;

public interface StatisticRepo extends JpaRepository<Statistic, Integer> {

}
