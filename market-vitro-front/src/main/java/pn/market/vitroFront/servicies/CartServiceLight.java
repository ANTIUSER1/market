package pn.market.vitroFront.servicies;

import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Mono;

public interface CartServiceLight {

    Mono<Item> placeItemToCart(long itemId, String action);
}
