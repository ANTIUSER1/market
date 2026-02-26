package pn.market.vitroFront.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

import static pn.market.vitroFront.config.AuthPaths.VITRO_TEST_API;

@RestController
@RequestMapping("/test-api")
public class HomeRest {
    @Autowired
    @Qualifier("BACK")
    WebClient webClient;


    @GetMapping("/time")
    public Mono<Date> testRest() {
        return Mono.just(new Date());
    }


    @GetMapping("/time-1")
    public Mono<Date> testRest11() {
        return webClient.get()
                .uri(VITRO_TEST_API + "/time")
                .retrieve().bodyToMono(Date.class);
    }

    @GetMapping("/date/{n}")
    public Mono<Date> testRest1Date1(@PathVariable("n") int n) {
        return webClient.put()
                .uri(VITRO_TEST_API + "/date/" + n

                )
                .retrieve().bodyToMono(Date.class);
    }

}
