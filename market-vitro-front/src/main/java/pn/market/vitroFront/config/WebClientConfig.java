package pn.market.vitroFront.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {

    @Bean
    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
            ReactiveClientRegistrationRepository clientRegistrations,
            ServerOAuth2AuthorizedClientRepository authorizedClients) {
        var authorizedClientProvider = ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
                .clientCredentials()
                .build();
        var authorizedClientManager = new DefaultReactiveOAuth2AuthorizedClientManager(
                clientRegistrations, authorizedClients);
        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
        return authorizedClientManager;
    }

    @Bean
    public WebClient webClient(ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
        // Создаём функцию-фильтр для WebClient, которая будет автоматически
        // запрашивать и прикреплять OAuth2-токены к каждому HTTP-запросу
        var oauth2Client = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        // Указываем ID регистрации OAuth2-клиента по умолчанию (должен совпадать с именем в application.yml)
        oauth2Client.setDefaultClientRegistrationId("keycloak");
        return WebClient.builder()
                // Добавляем OAuth2-авторизацию ко всем запросам
                .filter(oauth2Client)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024))
                .build();
    }
}
