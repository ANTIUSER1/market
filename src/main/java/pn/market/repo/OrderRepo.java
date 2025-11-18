package pn.market.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.entities.Order;

import java.util.List;

@Repository
@Transactional
public interface OrderRepo extends JpaRepository<Order, Long> {

    @Query("SELECT od FROM Order od ORDER BY od.id DESC")
    List<Order> getAllOrdersSortedDescById();

    @Query("SELECT od FROM Order od ORDER BY od.id ASC")
    List<Order> getAllOrdersSortedAscById();

    @Query("SELECT MAX(od.id) FROM Order od ")
    Long findMaxId();

}
