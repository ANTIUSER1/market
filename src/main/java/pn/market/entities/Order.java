package pn.market.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

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
    private List<Item> orderItems;

    public Order() {
        if (orderItems == null) orderItems = new ArrayList<>();
    }


    public void addItem(Item items) {
        orderItems.add(items);
    }

    public long getTotalSum() {
        return orderItems.parallelStream()
                .mapToLong(itm -> itm.getPrice()).sum();
    }

    public long getSize() {
        return orderItems.size();
    }

    @Override
    public String toString() {
        String sb = "Order{" + "id=" + id +
                ", orderItems=" + orderItems +
                '}';
        return sb;
    }
}
