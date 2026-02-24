package pn.market.market_entities.forWEB;

//import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;


@ToString
@Table(name = "orders", schema = "market")
public class Order {

    @Id
    private Long id;
    @Setter
    @Transient
    private Set<Item> items;


    public Order() {
        items = new TreeSet<>();
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

    public Set<Item> getItems() {
        return items;
    }

    public void setItems(Set<Item> items) {
        this.items = items;
    }


    public void addItem(Item item) {
        items.add(item);
    }


    public void addItemsSet(List<Item> itemsList) {
        items.addAll(itemsList);
    }

    public long getTotalSumm() {
        return items.stream().mapToLong(
                i -> i.getPrice() * i.getCount()
        ).sum();
    }

    @Override
    public String toString() {
        String sb = "Order{ " + "id=" + id +
                ", items=" + items +
                ", totalSum=" + getTotalSumm() +
                " } ";
        return sb;
    }
}
