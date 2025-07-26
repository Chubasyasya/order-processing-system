package kpfu.itis.allayarova.config;

import kpfu.itis.allayarova.data.model.OrderEntity;
import kpfu.itis.allayarova.data.view.OrderView;
import kpfu.itis.allayarova.mapper.OrderMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }


}
