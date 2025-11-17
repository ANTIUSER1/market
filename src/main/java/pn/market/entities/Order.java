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


    @OneToMany(cascade = CascadeType.ALL)
    private List<Item> itemList;

    public Order() {
        if (itemList == null) itemList = new ArrayList<>();
    }

    public void addItem(Item item) {
        itemList.add(item);
    }

    public long getTotalSum() {
        return itemList.parallelStream()
                .mapToLong(itm -> itm.getPrice()).sum();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("id=").append(id);
        sb.append(", itemList=").append(strOfitemList());
        sb.append('}');
        return sb.toString();
    }

    private String strOfitemList() {
        StringBuffer sbf = new StringBuffer("\n");
        for (Item item : itemList) {
            sbf.append(item.toString()).append("\n");
        }
        return sbf.toString();
    }
}
