package pn.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.payment.ent.User;
import pn.payment.services.UserService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserRest {
    @Autowired
    private UserService userService;

    @GetMapping("/create")
    public Mono<User> getUsers() {
        return userService.create();
    }
}
