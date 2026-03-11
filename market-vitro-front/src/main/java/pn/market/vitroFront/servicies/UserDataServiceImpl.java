package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.data.UserData;
import reactor.core.publisher.Mono;

import static pn.market.vitroFront.config.AuthPaths.VITRO_USERS_API;

@Service
public class UserDataServiceImpl {

    @Value("${oauth.data.host}")
    private String auth2Host;

    @Autowired
    //@Qualifier("BACK")
    private WebClient webClient;

    public Mono<UserData> findUser(String name) {
        return webClient
                .get()
                .uri(auth2Host + VITRO_USERS_API + "/ud/" + name)
                .retrieve()
                .bodyToMono(UserData.class);
    }


}
