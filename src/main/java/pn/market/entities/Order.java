package pn.market.entities;

//import jakarta.persistence.*;

import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;


@ToString
@Table(name = "orders", schema = "market")
public class Order {

    @Id
    private Long id;

    private List<Item> items;


    public Order() {
        items = new ArrayList<>();
    }

    public Order(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
