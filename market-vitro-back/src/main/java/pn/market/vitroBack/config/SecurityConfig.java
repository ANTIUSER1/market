package pn.market.vitroBack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
// Обязательно включаем защиту для WebFlux
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {

        /*
         .pathMatchers(
                        HttpMethod.POST, HttpMethod.GET,
                        HttpMethod.PUT "/**"
                )
         */
        //http.authorizeExchange()
        http.authorizeExchange(exchanges -> exchanges

                .pathMatchers(HttpMethod.GET, "/**").permitAll()
                .pathMatchers(HttpMethod.POST, "/**").permitAll()
                .pathMatchers(HttpMethod.PUT, "/**").permitAll()
                // Защищаем любое взаимодействие
                .anyExchange().authenticated()
        );

        http.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(Customizer.withDefaults())
        );
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var converter = new JwtGrantedAuthoritiesConverter();
        // Убираем префиск, чтобы роли были "SERVICE", а не "ROLE_SERVICE"
        converter.setAuthorityPrefix("");
        // Указываем Spring, откуда брать роли
        converter.setAuthoritiesClaimName("resource_access.backend-service.roles");
        return new JwtAuthenticationConverter() {{
            setJwtGrantedAuthoritiesConverter(converter);
        }};
    }
}