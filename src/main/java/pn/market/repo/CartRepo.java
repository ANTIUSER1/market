package pn.market.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pn.market.entities.Cart;
import pn.market.entities.Item;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {
    @Query("SELECT i FROM Item i ORDER BY i.id DESC")
    List<Cart> getAllCartSortedDescById();
    @Query("SELECT i FROM Item i ORDER BY i.id ASC")
    List<Cart> getAllCartSortedAscById();

}
