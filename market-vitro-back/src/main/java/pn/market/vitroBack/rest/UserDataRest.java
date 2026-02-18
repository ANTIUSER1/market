package pn.market.vitroBack.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pn.market.market_entities.data.UserData;
import pn.market.vitroBack.servicies.impl.UserEntityServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/vitro/users")
public class UserDataRest {

    @Autowired
    private UserEntityServiceImpl userEntityService;

    @GetMapping
    public Flux<UserData> findAllUsers() {
        return userEntityService.findAllUsers();
    }

    @GetMapping("/ud/{name}")
    public Mono<UserData> findUserDataByName(@PathVariable("name") String name) {
        return userEntityService.findByName(name);
    }

    @GetMapping("/ud/add/{n}/{authority}")
    public Flux<UserData> addUsersSet(
            @PathVariable("n") int n,
            @PathVariable("authority") String authority
    ) {
        System.out.println("\n ADD : " + n + "  AUTS: " + authority);
        return userEntityService.addUserDataSet(n, authority);
    }

}
