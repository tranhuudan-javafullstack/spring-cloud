package huudan.io.accountservice.client;

import huudan.io.accountservice.model.StatisticDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings("deprecation")
@FeignClient(name = "statistic-service", fallback = StatisticServiceImpl.class,
        configuration = StatisticFeignClientConfiguration.class)
public interface StatisticService {

    @PostMapping("/statistic")
    void add(@RequestBody StatisticDTO statisticDTO);
}

@Component
@Slf4j
class StatisticServiceImpl implements StatisticService {

    @Override
    public void add(StatisticDTO statisticDTO) {
        // fallback
        log.error("Statistic service is slow");
    }
}

class StatisticFeignClientConfiguration {
//
//    private final String CLIENT_REGISTRATION_ID = "a-service";
//
//    @Bean
//    RequestInterceptor requestInterceptor(OAuth2AuthorizedClientManager authorizedClientManager) {
//        return new OAuth2AccessTokenInterceptor(CLIENT_REGISTRATION_ID, authorizedClientManager);
//    }
}
