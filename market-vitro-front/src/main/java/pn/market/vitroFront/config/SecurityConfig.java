package pn.market.vitroFront.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository;

import java.net.URI;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
//@EnableMethodSecurity
public class SecurityConfig {
    // Защищаем пароли шифрованием

    @Bean
    public WebSessionServerCsrfTokenRepository csrfTokenRepository() {
        return new WebSessionServerCsrfTokenRepository();
    }


    // Настраиваем поведение при выходе
    @Bean
    public RedirectServerLogoutSuccessHandler redirectServerLogoutSuccessHandler() {
        RedirectServerLogoutSuccessHandler logoutSuccessHandler = new RedirectServerLogoutSuccessHandler();
        // При выходе перенаправляем его на домашнюю страницу
        logoutSuccessHandler.setLogoutSuccessUrl(URI.create("/"));
        return logoutSuccessHandler;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            RedirectServerLogoutSuccessHandler redirectServerLogoutSuccessHandler) {

        http.authorizeExchange(exchanges -> exchanges
                .pathMatchers("/", "/items/*", "/login").permitAll()
                .anyExchange().authenticated()
        );

        http.formLogin(f -> f.loginPage("/login"));
        http.logout(logout -> logout
                // URL страницы выхода
                .logoutUrl("/logout")
                .logoutSuccessHandler(redirectServerLogoutSuccessHandler)
        );
        http.oauth2Client(withDefaults());
        http.logout(logout -> logout
                        .logoutUrl("/"))
                // Настройка security-заголовков
                .headers(headers -> headers
                                .frameOptions(Customizer.withDefaults())
                );
        return http.build();
    }
}
