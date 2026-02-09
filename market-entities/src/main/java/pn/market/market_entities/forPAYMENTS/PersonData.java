package pn.market.market_entities.forPAYMENTS;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@ToString
@Table("persons")
public class PersonData {

    @Id
    private long id;
    private String login;
    private String password;
    private Long moneySupply;

    public PersonData() {
    }

    public PersonData(String login, String password, Long moneySupply) {
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
        String sb = "PersonData{" + "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", moneySupply=" + moneySupply +
                '}';
        return sb;
    }

    public PersonData addMoney(Long money) {
        this.moneySupply += money;
        return this;
    }

    public PersonData removeMoney(Long money) {
        if (this.moneySupply > money) this.moneySupply -= money;
        return this;
    }
}
