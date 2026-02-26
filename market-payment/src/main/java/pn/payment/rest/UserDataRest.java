package pn.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pn.market.market_entities.forPAYMENTS.PersonData;
import pn.payment.services.UserDataService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/payment/users")
public class UserDataRest {


    @Autowired
    private UserDataService userDataService;

    @PostMapping("/create")
    public Mono<PersonData> getUsers() {
        return userDataService.create();
    }

    @GetMapping("/find/{id}")
    public Mono<PersonData> findUserById(
            @PathVariable("id") long id
    ) {
        return userDataService.findById(id);
    }

    @GetMapping("/add-money/{id}/{money}")
    public Mono<PersonData> addMoney(
            @PathVariable("id") long id,
            @PathVariable("money") long money
    ) {
        return userDataService.addMoney(id, money);
    }

    @GetMapping("/remove-money/{id}/{money}")
    public Mono<PersonData> removeMoney(
            @PathVariable("id") long id,
            @PathVariable("money") long money
    ) {
        System.out.println("   REMOVE  MONEY "+money);
        return userDataService.removeMoney(id, money);
    }

    @GetMapping("/remove-money-for-order")
    public Mono<Boolean> removeMoneyForOrder() {
        System.out.println( " /remove-money-for-order "  );
        return userDataService.removeMoneyForOrder();

    }

    @GetMapping("/remove-money-from-first-success/{money}")
    public Mono<Boolean> removeMoneyFromFirstSuccsess(
            @PathVariable("money") long money
    ) {
        return userDataService.removeMoneySuccess(1, money);
    }

    @GetMapping("/ooooo")
    public String hoo() {
        return "ooo";
    }
}
