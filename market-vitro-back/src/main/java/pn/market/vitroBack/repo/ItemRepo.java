package pn.market.vitroBack.repo;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;

@Repository
@Transactional
public interface ItemRepo extends ReactiveCrudRepository<Item, Long> {


    @Query(
            """ 
                    SELECT * FROM items i  WHERE i.id in  (
                          SELECT   DISTINCT ci.item_id   FROM carts_items ci
                              WHERE    ci.cart_id  
                               IN ( SELECT cart_id FROM user_data ud WHERE  ud.id =  $1  )   )
                    
                    """)
    Flux<Item> findItemsByUserId(Long userId);


    @Query(
            """ 
                    select * from items i  where i.id in (\s
                         select distinct oi.item_id  from orders_items  oi where oi.order_id = $1
                         )
                    """)
    Flux<Item> findItemsByOrderId(Long orderId);

    @Query(
            """ 
                    select * from items i  where i.id in (\s
                         select distinct oi.item_id  from carts_items  oi where oi.cart_id = $1
                         ) 
                    """)
    Flux<Item> findItemsByCartId(Long cartId);


    @Query("SELECT * FROM items i WHERE i.order_id = :orderId  ORDER BY i.id ASC")
    Flux<Item> findByOrderId(Long orderId);


    Flux<Item> findAllBy(Pageable pageable);

}
