package pn.market.entities;

//import jakarta.persistence.*;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@ToString
@Table(name = "orders", schema = "market")
public class Order {

    @Id
    private Long id;


    public Order() {
    }

    public Order(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
