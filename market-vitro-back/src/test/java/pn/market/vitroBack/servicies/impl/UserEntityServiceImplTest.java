package pn.market.vitroBack.servicies.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.data.UserData;
import pn.market.vitroBack.repo.UserDataRepo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class UserEntityServiceImplTest {
    @MockitoBean
    private UserDataRepo userDataRepo;

    private UserData userData;
    private Mono<UserData> userDataMono;
    private List<UserData> userDataList;
    private Flux<UserData> userDataFlux;


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
    void findAllUsers() {
        Mockito.when(userDataRepo.findAllUsers())
                .thenReturn(userDataFlux);
        Assertions.assertEquals(userDataFlux, userDataRepo.findAllUsers());
    }

    @Test
    void findByName() {
        Mockito.when(userDataRepo.findByName("a"))
                .thenReturn(userDataMono);
        Assertions.assertEquals(userDataMono, userDataRepo.findByName("a"));
    }


}