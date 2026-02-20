package pn.market.vitroBack.repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import pn.market.market_entities.forWEB.CartItems;

public interface CartItemsRepo extends ReactiveCrudRepository<CartItems, Long> {
}
