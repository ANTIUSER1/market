package pn.market.market_entities.forWEB;

//import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;
import java.util.TreeSet;

@ToString
@Table(name = "carts", schema = "market")
public class Cart implements Comparable<Cart> {


    @Id
    @Getter
    private Long id;


    @Setter
    @Getter
    @Transient
    private Set<Item> items;

    public Cart() {
        items = new TreeSet<>();
    }

    public Integer getCount() {
        return items.size();
    }

    public long getTotalSumm() {
        return items.stream().mapToLong(
                i -> i.getPrice() * i.getCount()
        ).sum();
    }

    @Override
    public int compareTo(Cart o) {
        return (int) (this.id - o.id);
    }


}
