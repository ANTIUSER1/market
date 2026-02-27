package pn.market.vitroFront.config;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.authorization.event.AuthorizationEvent;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;
import pn.market.vitroFront.events.EventsHandler;

@Configuration
public class EventPublisherConfig {

    @Bean
    public AuthorizationEventPublisher authorizationEventPublisher(
            ApplicationEventPublisher applicationEventPublisher
    ) {
        // Используем стандартный публикатор событий авторизации Spring Security
        applicationEventPublisher.publishEvent(AuthorizationGrantedEvent.class);
        applicationEventPublisher.publishEvent(AuthorizationEvent.class);
        applicationEventPublisher.publishEvent(EventsHandler.class);
       System.out.println("\t\t -----EVENT PUBLISHER CONFIG--- ");
        return new SpringAuthorizationEventPublisher(applicationEventPublisher);
    }

}
