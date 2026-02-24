package pn.market.vitroBack.repo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.market_entities.forWEB.Order;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public interface OrderRepo extends ReactiveCrudRepository<Order, Long> {


    @Query("SELECT * FROM orders o  WHERE  o.user_id = :userId ORDER BY o.id ASC")
    Flux<Order> findByUserId(Long userId);

    @Query("""
             select sum(i.count *i.price)  as cp from items i  where i.id in (
               select distinct oi.item_id  from orders_items  oi where oi.order_id = $1
               )
            
            """)
    Mono<Long> calcTotalSumOfOrderId(Long orderId);


}
