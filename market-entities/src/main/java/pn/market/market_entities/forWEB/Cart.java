package pn.market.market_entities.forWEB;

//import jakarta.persistence.*;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.TreeSet;


@ToString
@Table(name = "carts", schema = "market")
public class Cart implements Comparable<Cart> {

    @Getter
    @Transient
    private final Set<Item> items;

    @Id
    @Getter
    private Long id;


    public Cart() {
        items = new TreeSet<>();
    }

    public Cart(Long id) {
        this();
        this.id = id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void addItem(Item item) {
        items.add(item);
    }


    @Override
    public int compareTo(Cart o) {
        return (int) (this.id - o.id);
    }
}
