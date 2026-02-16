package pn.market.market_entities.forWEB;

//import jakarta.persistence.*;

import lombok.Setter;
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
    @Setter
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


    public void addItem(Item item) {
        items.add(item);
    }

    public long totalSumm() {
        return items.stream().mapToLong(
                i -> i.getPrice() * i.getCount()
        ).sum();
    }

    @Override
    public String toString() {
        String sb = "Order{ " + "id=" + id +
                ", items=" + items +
                ", totalSum=" + totalSumm() +
                " } ";
        return sb;
    }
}
