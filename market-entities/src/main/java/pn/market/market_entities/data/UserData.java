package pn.market.market_entities.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Getter
@ToString
@Table("user_data")
public class UserData implements UserDetails {

    @Id
    private Long id;
    private String username;
    private String password;
    @Setter
    private String authority;

    private Long money;

    public UserData() {
    }

    public UserData(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }

    @NullMarked
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authority == null) return new HashSet<>();
        return Arrays.stream(authority.trim().split(","))
                .map(s -> new GrantedAuthorityImpl(s))
                .collect(Collectors.toSet());
    }
}
