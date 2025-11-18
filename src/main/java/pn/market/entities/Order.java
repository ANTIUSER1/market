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
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<Item> itemList;

    public Order() {
        if (itemList == null) itemList = new ArrayList<>();
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public void addItemList(List<Item> items) {
        itemList.addAll(items);
    }

    public long getTotalSum() {
        return itemList.parallelStream()
                .mapToLong(itm -> itm.getPrice()).sum();
    }



}
