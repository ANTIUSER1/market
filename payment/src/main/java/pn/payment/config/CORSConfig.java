package pn.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.util.logging.Logger;

@Configuration
public class CORSConfig implements WebFluxConfigurer {


    Logger log = Logger.getLogger(CORSConfig.class.getName());
    @Value("${remote-server}")
    private String remoteServer;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        log.info(" \n\t Remote Access from  " + remoteServer + "   ---  ");

        registry.addMapping("/**")
                .allowedOrigins(remoteServer)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
