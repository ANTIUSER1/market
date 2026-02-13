package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pn.market.vitroBack.servicies.TimeService;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/test")
public class TimeRest {

    @Autowired
    private TimeService service;

    @GetMapping("/time")
    public Mono<LocalDateTime> test() {
        return service.test();
    }

    @PutMapping("/date/{n}")
    public Mono<Date> test1(@PathVariable("n") int n) {
        return service.test1(n);
    }
}
