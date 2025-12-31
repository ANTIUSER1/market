package pn.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pn.payment.ent.PersonData;
import pn.payment.services.PersonService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
public class PersonRest {


    @Autowired
    private PersonService personService;

    @PostMapping("/create")
    public Mono<PersonData> getUsers() {
        return personService.create();
    }

    @GetMapping("/find/{id}")
    public Mono<PersonData> findUserById(
            @PathVariable("id") long id
    ) {
        return personService.findById(id);
    }

    @GetMapping("/add-money/{id}/{money}")
    public Mono<PersonData> addMoney(
            @PathVariable("id") long id,
            @PathVariable("money") long money
    ) {
        return personService.addMoney(id, money);
    }

    @GetMapping("/add-money-to-first/{money}")
    public Mono<PersonData> addMoneyToFirst(
            @PathVariable("money") long money
    ) {
        return personService.addMoney(1L, money);
    }

    @GetMapping("/remove-money/{id}/{money}")
    public Mono<PersonData> removeMoney(
            @PathVariable("id") long id,
            @PathVariable("money") long money
    ) {
        return personService.removeMoney(id, money);
    }


    @GetMapping("/remove-money-for-order")
    public Mono<PersonData> removeMoneyForOrder() {
        personService.removeMoneyForOrder();
        return Mono.empty();

        // return userService.removeMoney(id, money);
    }

    @GetMapping("/remove-money-from-first/{money}")
    public Mono<PersonData> removeMoneyFromFirst(
            @PathVariable("money") long money
    ) {
        return personService.removeMoney(1, money);
    }

    @GetMapping("/remove-money-from-first-success/{money}")
    public Mono<Boolean> removeMoneyFromFirstSuccsess(
            @PathVariable("money") long money
    ) {
        return personService.removeMoneySuccess(1, money);
    }
}
