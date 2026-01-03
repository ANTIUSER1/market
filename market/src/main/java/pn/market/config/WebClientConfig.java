package pn.market.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.logging.LogRecord;

@Configuration
public class WebClientConfig  {//} implements  WebFluxConfigurer {
    @Value("${remote-server}")
    private String remoteServer;

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl(remoteServer).build();
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins( "*" );
//    }
}
