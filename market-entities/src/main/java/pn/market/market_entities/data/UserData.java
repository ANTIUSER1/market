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
@Setter
@ToString
@Table("user_data")
public class UserData implements UserDetails {

    @Id
    private Long id;
    private String username;
    private String password;

    private String authority;

    private Long money;

    private Long cartId;

    private Long orderId;

    public UserData() {
        this.money = 100_000_000L;
    }

    public UserData(String username, String password, String authority) {
        this();
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

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public UserData addMoney(Long m) {
        this.money += m;
        return this;
    }

    public UserData removeMoney(Long m) {
        this.money -= m;
        return this;
    }
}
