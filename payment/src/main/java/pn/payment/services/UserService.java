package pn.payment.services;

import org.springframework.boot.webflux.autoconfigure.WebFluxProperties;
import org.springframework.stereotype.Service;
import pn.payment.ent.User;
import reactor.core.publisher.Mono;

@Service
public class UserService {


    public Mono<User> create(){
        User result=new User("U","p",   100_000L)    ;
        return Mono.just(result);

    }
}
