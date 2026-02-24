package pn.market.vitroBack.repo;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pn.market.market_entities.forWEB.OrderItems;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface OrderItemsRepo extends ReactiveCrudRepository<OrderItems, Long> {

    @Query("SELECT * FROM orders_items ci WHERE ci.order_id = $1    ")
    Flux<OrderItems> findByOrderId(Long orderId);

    @Query("SELECT * FROM orders_items ci WHERE ci.item_id = $1    ")
    Flux<OrderItems> findByItemId(Long itemId);

    @Query("SELECT COUNT(*) FROM orders_items ci WHERE ci.item_id = $1 AND  ci.order_id = $2    ")
    Mono<Long> countOfOrderAndItemId(Long itemId, Long orderId);
}
