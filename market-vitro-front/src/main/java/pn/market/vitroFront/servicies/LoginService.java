package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import pn.market.market_entities.data.UserData;
import reactor.core.publisher.Mono;

@Service
public class LoginService extends MapReactiveUserDetailsService {

    @Autowired
    private UserDataServiceImpl userDataService;

    private UserData userData;

    public LoginService() {
        super(User.withUsername(" ").build());
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userDataService.findUser(username)
                .map(this::convertToUserDetails);
    }

    private UserDetails convertToUserDetails(UserData userData) {
        if (userData.getAuthority() == null || userData.getAuthority().trim().isEmpty())
            userData.setAuthority("EMPTY");
System.out.println(
        "     USER_DATA "+userData
);
        this.userData = userData;

       return User.withUsername(userData.getUsername())
                      .password(userData.getPassword())
                      .accountExpired(false)
                      .accountLocked(false)
                      .credentialsExpired(false)
                      .disabled(false)
                      .build();
    }

    public UserData getUserData() {
        return userData;
    }
}
