package huudan.io.statisticservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import huudan.io.statisticservice.model.StatisticDTO;
import huudan.io.statisticservice.service.StatisticService;

@RestController
public class StatisticController {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private StatisticService statisticService;

    // Add new
    @PreAuthorize("hasAuthority('SCOPE_log')")
    @PostMapping("/statistic")
    public StatisticDTO add(@RequestBody StatisticDTO statisticDTO) {
        logger.info("StatisticController: Add statistic");

//	try {
//	    Thread.sleep(10000);
//	} catch (InterruptedException e) {
//	    // TODO Auto-generated catch block
//	    e.printStackTrace();
//	}

        statisticService.add(statisticDTO);
        return statisticDTO;
    }

    // get all
    @PreAuthorize("hasAuthority('SCOPE_read') && hasRole('ADMIN')")
    @GetMapping("/statistics")
    public List<StatisticDTO> getAll() {
        logger.debug("Get all statistic");

        return statisticService.getAll();
    }
}
