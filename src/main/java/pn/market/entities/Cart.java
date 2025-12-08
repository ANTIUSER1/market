package pn.market.entities;

//import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;


@ToString
@Table(name = "carts", schema = "market")
public class Cart {

    @Id
    private Long id;

    private List<Item> cartItems = new ArrayList<>();

    public Cart() {
        cartItems = new ArrayList<>();
    }

    public Cart(Long id) {
        this();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<Item> cartItems) {
        this.cartItems = cartItems;
    }

    public void plusItem(Item item) {
        cartItems.add(item);
    }

    public void minusItem(Item item) {
        cartItems.remove(item);
    }

    public long getTotalSum() {
        return cartItems.parallelStream()
                .mapToLong(itm -> itm.getCount() * itm.getPrice())
                .sum();
    }

    public long getSize() {
        return cartItems.size();
    }

    public boolean isiTtemInCart(Item item) {
        return cartItems.contains(item);
    }
}
