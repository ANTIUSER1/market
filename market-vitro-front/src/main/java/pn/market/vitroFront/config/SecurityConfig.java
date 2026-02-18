package pn.market.vitroFront.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository;

import java.net.URI;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    // Защищаем пароли шифрованием
  /*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Создаём in-memory пользователя
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("a")
                .password(passwordEncoder().encode("a"))
                .roles("USER")
                .build();
        return new MapReactiveUserDetailsService(user);
    }
    */

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
                .pathMatchers("/", "/login").permitAll()
                .anyExchange().authenticated()
        );

//        http.csrf(csrf -> csrf
//                .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
//        );
//        http.authorizeExchange(exchanges -> exchanges
////                .pathMatchers(
////                        "/login").permitAll()
//                        .anyExchange().authenticated()
//        );
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
                        //   .frameOptions().disable()
                        //  )

                        // Регистрируем OidcClientInitiatedServerLogoutSuccessHandler
                        // .logoutSuccessHandler(oidcLogoutSuccessHandler())
                );
        return http.build();
       /*
        http
                .csrf(csrf -> csrf
                        .csrfTokenRepository(CookieServerCsrfTokenRepository.withHttpOnlyFalse())
                )
                // Явно разрешаем доступ к /login и / для всех
                .authorizeExchange(exchanges -> exchanges
                        //  .pathMatchers("/", "/login").permitAll()
                        .anyExchange().authenticated()
                )
                // Настраиваем форму логина
                .formLogin(form -> form
                        // URL страницы логина
                        .loginPage("/login")
                        .authenticationSuccessHandler(
                                // В случае успешного логина перенаправляем на /message
                                new RedirectServerAuthenticationSuccessHandler("/message")
                        )
                )
                // Настраиваем обработку при выходе
                .logout(logout -> logout
                        // URL страницы выхода
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(redirectServerLogoutSuccessHandler)
                )
                // OAuth2 Client для WebClient
                .oauth2Client(withDefaults());
        return http.build();

         */
    }
}
