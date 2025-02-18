package huudan.io.accountservice.client;

import huudan.io.accountservice.model.StatisticDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@SuppressWarnings("deprecation")
@FeignClient(name = "statistic-service", fallback = StatisticServiceImpl.class,
        configuration = StatisticFeignClientConfiguration.class )
public interface StatisticService {

    @PostMapping("/statistic")
    void add(@RequestBody StatisticDTO statisticDTO);
}

@Component
class StatisticServiceImpl implements StatisticService {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void add(StatisticDTO statisticDTO) {
        // fallback
        logger.error("Statistic service is slow");
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
