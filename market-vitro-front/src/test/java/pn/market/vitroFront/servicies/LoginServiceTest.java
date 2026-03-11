package pn.market.vitroFront.servicies;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import pn.market.market_entities.data.UserData;
import reactor.core.publisher.Mono;

@SpringBootTest
class LoginServiceTest {

    @MockitoBean
    private UserDataServiceImpl userDataService;

    @MockitoBean
    private LoginService loginService;


    private Mono<UserDetails> userDetailsMono;
    private Mono<UserData> userDetaMono;
    private UserData ud;

    @BeforeEach
    void init() {
        ud = new UserData();
        ud.setUsername("un");
        userDetaMono = Mono.just(ud);
        UserDetails udtl = User.withUsername("un").build();
        userDetailsMono = Mono.just(udtl);
    }

    @Test
    void findByUsername() {
        Mockito.when(userDataService.findUser("un"))
                .thenReturn(userDetaMono);
        Assertions.assertEquals(userDetaMono.blockOptional().get().getUsername(),
                userDataService.findUser("un").block().getUsername());
    }

}