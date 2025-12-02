package pn.market.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.entities.Cart;

@Repository
@Transactional
public interface CartRepo extends JpaRepository<Cart, Long> {

    @Query("SELECT MAX(c.id) FROM Cart c ")
    long findMaxId();
}
