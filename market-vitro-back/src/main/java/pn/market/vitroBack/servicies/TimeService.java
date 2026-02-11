package pn.market.vitroBack.servicies;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class TimeService {

    public Mono<LocalDateTime> test() {
        return Mono.just(LocalDateTime.now());
    }

}
