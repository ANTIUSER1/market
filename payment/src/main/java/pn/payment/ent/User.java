package pn.payment.ent;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("users")
public class User {

    @Id
    private long id;
    private String login;
    private String password;
    private Long moneySupply;

    public User() {
    }

    public User(String login, String password, Long moneySupply) {
        this.login = login;
        this.password = password;
        this.moneySupply = moneySupply;
    }

    public long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Long getMoneySupply() {
        return moneySupply;
    }

    @Override
    public String toString() {
        String sb = "User{" + "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", moneySupply=" + moneySupply +
                '}';
        return sb;
    }

    public User addMoney(Long money) {
        this.moneySupply += money;
        return this;
    }

    public User removeMoney(Long money) {
        if (this.moneySupply > money) this.moneySupply -= money;
        return this;
    }
}
