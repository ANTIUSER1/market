package pn.payment.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.forPAYMENTS.PersonData;
import pn.payment.repo.UserDataRepo;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class PersonServiceTest {


    @MockitoBean
    private UserDataRepo repo;
    @Autowired
    private UserDataService service;
    @MockitoBean
    private RedisTemplate<String, String> redisTemplate;

    @Test
    void create() {
        Mockito.when(repo.save(Mockito.any()))
                .thenReturn(Mono.just(new PersonData("", "", 2L)));
        Mockito.when(service.create())
                .thenReturn(Mono.just(new PersonData("", "", 2L)));
        assertNotNull(service.create());
    }

    @Test
    void addMoney() {
        Mockito.when(repo.save(Mockito.any()))
                .thenReturn(Mono.just(new PersonData("", "", 2L)));
        Mockito.when(service.addMoney(2L, 2L))
                .thenReturn(Mono.just(new PersonData("", "", 2L)));
        assertNotNull(service.addMoney(2L, 2L));

    }

    @Test
    void removeMoneyForOrder() {
//        Mockito.when(service.removeMoneySuccess(1L, 2L)).thenReturn(Mono.just(true));
//        assertTrue(service.removeMoneySuccess(2L, 2L)
//                .blockOptional().get());
        Mockito.when(redisTemplate.opsForValue().get("order:payment")).thenReturn("1");
        Mockito.when(service.removeMoneyForOrder()).thenReturn(Mono.just(true));
        Assertions.assertTrue(service.removeMoneyForOrder().blockOptional().get());
    }
}