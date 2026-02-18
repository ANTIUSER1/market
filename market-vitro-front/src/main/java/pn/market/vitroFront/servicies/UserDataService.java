package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.data.UserData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pn.market.vitroFront.config.AuthPaths.VITRO_USERS_API;

@Service
public class UserDataService {

    @Autowired
    private WebClient webClient;


    public Mono<UserData> findUser(String name) {
        return webClient
                .get()
                .uri(VITRO_USERS_API + "/ud/" + name)
                .retrieve()
                .bodyToMono(UserData.class);
    }

    public Flux<UserData> userEntSt() {
        return webClient
                .get()
                .uri(VITRO_USERS_API)
                .retrieve()
                .bodyToFlux(UserData.class);
    }

    public Mono<UserData> addUsersSet(int n, String authority) {
        return webClient
                .get()
                .uri(VITRO_USERS_API + "/ud/add/" + n + "/" + authority)
                .retrieve()
                .bodyToMono(UserData.class);
    }


}
