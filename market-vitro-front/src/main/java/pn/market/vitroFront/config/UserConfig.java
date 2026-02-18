package pn.market.vitroFront.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pn.market.vitroFront.servicies.UserDataService;


@Configuration
public class UserConfig {
    @Autowired
    private UserDataService userDataService;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
        //new BCryptPasswordEncoder();
    }


  /*
    @Bean
    public MapReactiveUserDetailsService userDetailsService(String name) {

      System.out.println("\n-------------LOGIN AS "+name);
        return userDataService.findUser(name)
                .map(
                        u->{
                            UserDetails user =
                                    User.withUsername(name)
                                            .password( "a")
                                            .roles("USER")
                                            .build();

                            return new MapReactiveUserDetailsService(u);
                        }
                ).block();
    }

   */
/*
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("a")
                .password(passwordEncoder().encode("a"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }

 */
}
