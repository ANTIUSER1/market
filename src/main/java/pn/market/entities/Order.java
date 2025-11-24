package pn.market.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER, orphanRemoval = false)
    @JoinColumn(name = "order_id")
    private List<Item> items;

    public Order() {
        if (items == null) items = new ArrayList<>();
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

    public void addItem(Item items) {
        this.items.add(items);
    }

    public long getTotalSum() {
        return items.parallelStream()
                .mapToLong(itm -> itm.getPrice()).sum();
    }

    public long getSize() {
        return items.size();
    }

    @Override
    public String toString() {
        String sb = "Order{" + "id=" + id +
                ", orderItems=" + items +
                '}';
        return sb;
    }
}
