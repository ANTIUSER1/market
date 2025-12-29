package pn.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pn.payment.ent.User;
import pn.payment.services.UserService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class UserRest {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public Mono<User> getUsers() {
        return userService.create();
    }

    @GetMapping("/find/{id}")
    public Mono<User> findUserById(
            @PathVariable("id") long id
    ) {
        return userService.findById(id);
    }

    @GetMapping("/add-money/{id}/{money}")
    public Mono<User> addMoney(
            @PathVariable("id") long id,
            @PathVariable("money") long money
    ) {
        return userService.addMoney(id, money);
    }

    @GetMapping("/add-money-to-first/{money}")
    public Mono<User> addMoneyToFirst(
            @PathVariable("money") long money
    ) {
        return userService.addMoney(1L, money);
    }

    @GetMapping("/remove-money/{id}/{money}")
    public Mono<User> removeMoney(
            @PathVariable("id") long id,
            @PathVariable("money") long money
    ) {
        return userService.removeMoney(id, money);
    }

    @GetMapping("/remove-money-from-first/{money}")
    public Mono<User> removeMoneyFromFirst(
            @PathVariable("money") long money
    ) {
        return userService.removeMoney(1, money);
    }
}
