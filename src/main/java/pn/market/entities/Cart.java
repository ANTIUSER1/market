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


    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<Item> cartItems = new ArrayList<>();




    public void addItem(Item item) {
        cartItems.add(item);
    }

    public long getTotalSum() {
        return cartItems.parallelStream()
                .mapToLong(itm -> itm.getPrice()).sum();
    }
}
