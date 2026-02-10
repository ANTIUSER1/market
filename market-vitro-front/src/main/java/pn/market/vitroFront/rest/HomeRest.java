package pn.market.vitroFront.rest;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Date;

@RestController
@RequestMapping("/test-api")
public class HomeRest {


    @GetMapping("/time")
    public Mono<Date> testRest() {
        return Mono.just(new Date());
    }
}
