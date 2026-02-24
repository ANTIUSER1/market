package pn.market.vitroBack.servicies.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pn.market.market_entities.data.UserData;
import pn.market.vitroBack.repo.UserDataRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserEntityServiceImpl {

    @Autowired
    private UserDataRepo userDataRepo;

    public Flux<UserData> findAllUsers() {
        return userDataRepo.findAllUsers();
    }

    public Mono<UserData> findByName(String name) {
        return userDataRepo.findByName(name);
    }

    public Mono<UserData> addUserData(String name, String autority) {
        UserData ud = new UserData(name, "p-" + name, autority);
        return userDataRepo.save(ud);
    }

    public Flux<UserData> addUserDataSet(int n, String autority) {
        List<UserData> result = new ArrayList<>(n);
        int K = (int) (1 + Math.random() * 100);
        for (int k = K; k < K + n; k++) {
            UserData ud = new UserData("u" + k, "p" + k, autority);
            result.add(ud);
        }
        return userDataRepo.saveAll(result);
    }

    public Mono<UserData> findUserById(Long userId) {
        return userDataRepo.findById(userId);
    }
}
