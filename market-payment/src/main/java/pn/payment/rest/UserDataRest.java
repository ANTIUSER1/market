package pn.payment.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.payment.services.UserDataService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/payment/users")
public class UserDataRest {


    @Autowired
    private UserDataService userDataService;

    @GetMapping("/remove-money-for-order")
    public Mono<Boolean> removeMoneyForOrder() {
        System.out.println(" /remove-money-for-order ");
        return userDataService.removeMoneyForOrder();

    }


}
