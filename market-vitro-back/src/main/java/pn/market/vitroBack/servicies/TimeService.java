package pn.market.vitroBack.servicies;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TimeService {

    public Mono<LocalDateTime> test() {
        return Mono.just(LocalDateTime.now());
    }

    public Mono<Date> test1(int n) {
        if (n < 10) return Mono.just(new Date(System.nanoTime()));
        return Mono.just(new Date(
                        (long) (
                                n + System.nanoTime()
                                        * Math.random()
                                        * Math.random()
                                        * Math.random()
                        )
                )
        );
    }

}
