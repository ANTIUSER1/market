package pn.market.vitroFront.events;

import org.springframework.context.event.EventListener;
import org.springframework.security.authorization.AuthorizationResult;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class EventsHandler {



    // Обработчик события отказа в доступе
    @EventListener
    public void handleAuthorizationDenied(AuthorizationDeniedEvent event) {
        // Извлекаем информацию о пользователе и ресурсе.
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

System.out.println("   AUTH PRINCIPAL "+auth.getPrincipal());
System.out.println("   AUTH NAME "+auth.getName());
                //event.getAuthentication().get();
        Object protectedResource = event.getObject();
        // Логируем факт отказа в доступе.
       System.out.println("Доступ ОТКЛОНЁН: пользователь " +
                       "не autorizovan для ресурса   " +
                auth.getName()  + "   "+protectedResource);
        // Можно добавить дополнительную логику, например увеличение счётчика метрик.
        // Отправка события в систему мониторинга или оповещение службы безопасности.
    }

    // Обработчик события успешной авторизации
    @EventListener
    public void handleAuthorizationGranted(AuthorizationEvent event) {
        Authentication auth = event
                .getAuthentication().get()      ;
        AuthorizationResult protectedResource = event.getAuthorizationResult();

        System.out.println(" \n\tпользователь  " +
                        " получил доступ к ресурсу   "+
                auth.getPrincipal() +" "+ protectedResource.isGranted()+"\n");
        // Здесь можно добавить действия для аудита успешных доступов.
        // Обратите внимание: данное событие будет публиковаться, только если оно включено специально.
    }
}
