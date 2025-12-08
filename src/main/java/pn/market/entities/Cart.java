package pn.market.entities;

//import jakarta.persistence.*;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@ToString
@Table(name = "carts", schema = "market")
public class Cart {

    @Id
    private Long id;

    public Cart() {
    }

    public Cart(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
