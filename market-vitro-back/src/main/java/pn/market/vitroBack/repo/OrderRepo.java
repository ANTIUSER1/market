package pn.market.vitroBack.repo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.market_entities.forWEB.Order;
import reactor.core.publisher.Mono;

@Repository
@Transactional
public interface OrderRepo extends ReactiveCrudRepository<Order, Long> {

    /*

     SELECT * FROM orders o  WHERE  id in(
     select ud.order_id   from user_data ud  where ud.id = 2
     )
     */
    @Query(
            """ 
                        SELECT * FROM orders o  WHERE  id in(
                           select ud.order_id   from user_data ud  where ud.id = $1
                             )
                    """)
    Mono<Order> findByUserId(Long userId);

    @Query("""
             select sum(i.count *i.price)  as cp from items i  where i.id in (
               select distinct oi.item_id  from orders_items  oi where oi.order_id = $1
               )
            
            """)
    Mono<Long> calcTotalSumOfOrderId(Long orderId);


}
