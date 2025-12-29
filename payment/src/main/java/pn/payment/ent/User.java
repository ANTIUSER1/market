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
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(", login='").append(login).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", moneySupply=").append(moneySupply);
        sb.append('}');
        return sb.toString();
    }

    public User addMoney(Long money){
        this.moneySupply += money;
        return this;
         }
    public User removeMoney(Long money){
        this.moneySupply -= money;
        return this;
    }
}
