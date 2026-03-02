package pn.market.vitroBack.repo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.data.UserData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserDataRepoNegativeTest {

    private UserData userData;
    private Mono<UserData> userDataMono;
    private List<UserData> userDataList;
    private Flux<UserData> userDataFlux;
    @MockitoBean
    private UserDataRepo userDataRepo;


    @BeforeEach
    void init() {
        userData = new UserData();
        userData.setUsername("a");
        userDataMono = Mono.just(userData);
        userDataList = new ArrayList<>();
        for (int k = 0; k < 5; k++) {
            userDataList.add(new UserData());
        }
        userDataFlux = Flux.fromIterable(userDataList);
    }

    @Test
    void findByName() {
        Mockito.when(userDataRepo.findByName("a"))
                .thenReturn(userDataMono);
        Assertions.assertNotEquals(Mono.empty(), userDataRepo.findByName("a"));
    }

    @Test
    void findAllUsers() {
        Mockito.when(userDataRepo.findAllUsers())
                .thenReturn(userDataFlux);
        Assertions.assertNotEquals(Flux.empty(), userDataRepo.findAllUsers());
    }
}
