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


    @Query(
            """ 
                        SELECT * FROM orders o  WHERE  id in(
                           select ud.order_id   from user_data ud  where ud.id = $1
                             )
                    """)
    Mono<Order> findByUserId(Long userId);


}
