package pn.payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
public class CORSConfig implements WebFluxConfigurer {
    @Value("${remote-server}")
    private String remoteServer;

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        System.out.println("CORSConfig.addCorsMappings()::: " + remoteServer + "   ---  ");
        System.out.println("CORSConfig.addCorsMappings()::: " + remoteServer + "   ---  ");
        System.out.println("CORSConfig.addCorsMappings()::: " + remoteServer + "   ---  ");
        System.out.println("CORSConfig.addCorsMappings()::: " + remoteServer + "   ---  ");

        registry.addMapping("/**")
                .allowedOrigins(remoteServer)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
