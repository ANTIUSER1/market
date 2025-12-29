package pn.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.autoconfigure.WebFluxProperties;
import org.springframework.stereotype.Service;
import pn.payment.ent.User;
import pn.payment.repo.UserRepo;
import reactor.core.publisher.Mono;

@Service
public class UserService {


    @Autowired
    private UserRepo repo;

    public Mono<User> create(){
        User result=new User("U","p",   100_000L)    ;
        return repo.save(result);

    }
}
