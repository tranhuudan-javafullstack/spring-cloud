package huudan.io.statisticservice.controller;

import huudan.io.statisticservice.model.StatisticDTO;
import huudan.io.statisticservice.service.StatisticService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StatisticController {

    StatisticService statisticService;


    // Add new
    @PreAuthorize("hasAuthority('SCOPE_log')")
    @PostMapping("/statistic")
    public StatisticDTO add(@RequestBody StatisticDTO statisticDTO) {
        log.info("StatisticController: Add statistic");

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
        log.debug("Get all statistic");

        return statisticService.getAll();
    }
}
