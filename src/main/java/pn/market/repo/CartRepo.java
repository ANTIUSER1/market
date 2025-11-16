package pn.market.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pn.market.entities.Cart;

import java.util.List;

public interface CartRepo extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c ORDER BY c.id DESC")
    List<Cart> getAllCartSortedDescById();

    @Query("SELECT c FROM Cart c ORDER BY c.id ASC")
    List<Cart> getAllCartSortedAscById();

}
