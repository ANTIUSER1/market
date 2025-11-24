package pn.market.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@ToString
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "cart_id")
    private List<Item> cartItems = new ArrayList<>();

    public Cart() {
        cartItems = new ArrayList<>();
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
                .mapToLong(itm -> itm.getCount()* itm.getPrice())
                 .sum();
    }

    public long getSize() {
        return cartItems.size();
    }

    public boolean isiTtemInCart(Item item) {
        return cartItems.contains(item);
    }
}
