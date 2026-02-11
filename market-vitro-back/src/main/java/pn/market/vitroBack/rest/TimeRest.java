package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.vitroBack.servicies.TimeService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/vitro")
public class TimeRest {

    @Autowired
    private TimeService service;

    @GetMapping("/test-time")
    public Mono<LocalDateTime> test() {
        return service.test();
    }
}
