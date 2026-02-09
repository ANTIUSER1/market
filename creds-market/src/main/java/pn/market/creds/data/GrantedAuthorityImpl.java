package pn.market.creds.data;

import lombok.Getter;
import lombok.ToString;
import org.jspecify.annotations.Nullable;


@Getter
@ToString
public class GrantedAuthorityImpl implements add {

    private final String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    @Override
    public @Nullable String getAuthority() {
        return authority;
    }


}
