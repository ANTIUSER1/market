package pn.payment.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import pn.market.market_entities.data.UserData;
import pn.market.market_entities.forPAYMENTS.PersonData;
import pn.payment.repo.UserDataRepo;
import reactor.core.publisher.Mono;

@Service
public class UserDataService {


    @Value("${order-key}")
    private String orderKey;

    @Autowired
    private UserDataRepo repo;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


//    public Mono<UserData> create() {
//        UserData result = new UserData( );
//        return repo.save(result);
//    }

    public Mono<UserData> findById(long id) {
        return repo.findById(id);
    }

//    public Mono<UserData> addMoney(long id, long money) {
//        return repo.findById(id).map(u -> {
//            u.addMoney(money);
//            return u;
//        }).flatMap(repo::save);
//    }

    public Mono<UserData> removeMoney(long id, long money) {
        System.out.println("REmove MONEY EXECUTE "+id+"  MONEY "+money);
        return repo.findById(id).map(u -> {
            u.removeMoney( money);
            return u;
        }).flatMap(repo::save);
    }

    public Mono<Boolean> removeMoneySuccess(long id, long money) {
        System.out.println();
        System.out.println();
        return removeMoney(id, money).map(u -> {
            if (u.getMoney() < money) {
                return false;
            }
            return u.removeMoney(money).getMoney() >= 0;
        });
    }

    public Mono<Boolean> removeMoneyForOrder() {
        System.out.println("    personService.removeMoneyForOrder();    ");
        String s = redisTemplate.opsForValue().get(orderKey);
        System.out.println("REDIS DATA "+s);
        String[] split = s.split(";");
        long id = Long.parseLong(split[0]);
        long money = Long.parseLong(split[2]);
        Mono<Boolean> r = removeMoneySuccess(id, money);
        r.subscribe();
        return r;
    }
}
