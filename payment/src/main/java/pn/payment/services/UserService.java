package pn.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.payment.ent.User;
import pn.payment.repo.UserRepo;
import reactor.core.publisher.Mono;

@Service
public class UserService {


    @Autowired
    private UserRepo repo;

    public Mono<User> create() {
        User result = new User("U", "p", 100_000L);
        return repo.save(result);
    }

    public Mono<User> findById(long id) {
        return repo.findById(id);
    }

    public Mono<User> addMoney(long id, long money) {
        return repo.findById(id).map(u -> {
            u.addMoney(money);
            return u;
        }).flatMap(repo::save);
    }

    public Mono<User> removeMoney(long id, long money) {
        return repo.findById(id).map(u -> {
            u.removeMoney(money);
            return u;
        }).flatMap(repo::save);
    }

    public Mono<Boolean> removeMoneySuccess(long id, long money) {
        return removeMoney(id, money).map(u -> {
            if (u.getMoneySupply() < money) {
                return false;
            }
            return u.removeMoney(money).getMoneySupply() >= 0;
        });
    }
}
