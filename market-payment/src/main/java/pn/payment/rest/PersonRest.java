package pn.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pn.market.market_entities.forPAYMENTS.PersonData;
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

    @GetMapping("/remove-money/{id}/{money}")
    public Mono<PersonData> removeMoney(
            @PathVariable("id") long id,
            @PathVariable("money") long money
    ) {
        return personService.removeMoney(id, money);
    }

    @GetMapping("/remove-money-for-order")
    public Mono<Boolean> removeMoneyForOrder() {
        return personService.removeMoneyForOrder();

    }

    @GetMapping("/remove-money-from-first-success/{money}")
    public Mono<Boolean> removeMoneyFromFirstSuccsess(
            @PathVariable("money") long money
    ) {
        return personService.removeMoneySuccess(1, money);
    }

    @GetMapping("/ooooo")
    public String hoo() {
        return "ooo";
    }
}
