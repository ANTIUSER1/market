package pn.market.vitroBack.rest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.data.UserData;
import pn.market.vitroBack.servicies.impl.UserEntityServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDataRestTest {


    @MockitoBean
    private UserEntityServiceImpl userEntityService;

    Mono<UserData> userDataMono;
    Flux<UserData> userDataFlux;

    @BeforeEach
    void init(){
        userDataMono=Mono.just(new UserData());
        userDataFlux= Flux.fromIterable(new ArrayList<>());
    }

    @Test
    void findUserDataByName(){
        Mockito.when(   userEntityService.findByName("name") )
                .thenReturn(userDataMono);
        Assertions.assertEquals(userDataMono, userEntityService.findByName("name") );
    }
    @Test
    void addUsersSet(){
        Mockito.when( userEntityService.addUserDataSet( 3, "a") )
                .thenReturn(userDataFlux);
        Assertions.assertEquals(userDataFlux, userEntityService.addUserDataSet( 3, "a") );
    }
}