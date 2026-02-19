package pn.market.market_entities.data;

import lombok.Getter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Getter
@ToString
public class GrantedAuthorityImpl implements GrantedAuthority {

    @Getter
    private final String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }


}
