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

        System.out.println("*******\n  "
                + "\n   ID          " + userData.getId()
                + "\n   USERNAME    " + userData.getUsername()
                + "\n   PASSWORD    " + userData.getPassword()
                + "\n   AUTHORITIES " + userData.getAuthorities()
                + "\n   AUTHORITY   " + userData.getAuthority()
        );
        return User.withUsername(userData.getUsername())
                .password(userData.getPassword())
                // .authorities( userData.getAuthority())
                // .authorities(userData.getAuthorities())
//				   .authorities(List.of(
//						   new GrantedAuthorityImpl(AuthorityValues.ADMIN.name())))
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}
