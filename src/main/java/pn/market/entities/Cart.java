package pn.market.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<Item> itemList;

    public void addIte(Item item) {
        if (itemList == null) itemList = new ArrayList<>();
        itemList.add(item);
    }

    public long getTotalSum() {
        return itemList.parallelStream()
                .mapToLong(itm -> itm.getPrice()).sum();
    }
}
