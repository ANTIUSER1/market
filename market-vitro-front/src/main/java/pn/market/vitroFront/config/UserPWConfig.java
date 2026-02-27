package pn.market.vitroFront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class UserPWConfig {

@Bean
public   String securityCode(){
    return Long.toHexString(System.nanoTime());
}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //new BCryptPasswordEncoder();
    }

}
