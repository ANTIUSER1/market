package pn.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pn.payment.ent.PersonData;
import pn.payment.repo.PersonDataRepo;
import reactor.core.publisher.Mono;

@Service
public class PersonService {


    @Value("${order-key}")
    private String orderKey;

    @Autowired
    private PersonDataRepo repo;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    public Mono<PersonData> create() {
        PersonData result = new PersonData("U", "p", 100_000L);
        return repo.save(result);
    }

    public Mono<PersonData> findById(long id) {
        return repo.findById(id);
    }

    public Mono<PersonData> addMoney(long id, long money) {
        return repo.findById(id).map(u -> {
            u.addMoney(money);
            return u;
        }).flatMap(repo::save);
    }

    public Mono<PersonData> removeMoney(long id, long money) {
        return repo.findById(id).map(u -> {
            u.removeMoney(money);
            return u;
        }).flatMap(repo::save);
    }

    public Mono<Boolean> removeMoneySuccess(long id, long money) {
        System.out.println();
        System.out.println();
//        System.out.println("\t removeMoneySuccess " + id + " " + money);
//        System.out.println("removeMoneySuccess " + id + " " + money);
//        System.out.println("removeMoneySuccess " + id + " " + money);
        return removeMoney(id, money).map(u -> {
            if (u.getMoneySupply() < money) {
                return false;
            }
            return u.removeMoney(money).getMoneySupply() >= 0;
        });
    }

    public Mono<Boolean> removeMoneyForOrder() {
        String s = redisTemplate.opsForValue().get(orderKey);
        String[] split = s.split(";");
        long id = Long.parseLong(split[0]);
        long money = Long.parseLong(split[2]);
        Mono<Boolean> r = removeMoneySuccess(id, money);
        r.subscribe();
        return r;
    }
}
