package pn.market.entities;

//import jakarta.persistence.*;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;


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
        final StringBuffer sb = new StringBuffer(
                "Order{ ");
        sb.append("id=").append(id);
        sb.append(", items=").append(items);
        sb.append(", totalSum=").append(totalSumm());
        sb.append(" } ");
        return sb.toString();
    }
}
