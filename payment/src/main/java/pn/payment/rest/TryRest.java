package pn.payment.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/try")
public class TryRest {

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
