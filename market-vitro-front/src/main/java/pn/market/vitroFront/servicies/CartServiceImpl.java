package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.additional.ActionType;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static pn.market.vitroFront.config.AuthPaths.VITRO_CART_API;
import static pn.market.vitroFront.config.AuthPaths.VITRO_ITEM_API;

@Service
public class CartServiceImpl implements TService<Cart> {

    @Autowired
    @Qualifier("BACK")
    private WebClient webClient;

    @Override
    public Flux<Cart> findAll() {
        return null;
    }

    @Override
    public Mono<Cart> getById(Long id) {
        return null;
    }

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return null;
    }

    public Mono<Item> itemById(long itemId) {
       return webClient.get()
                .uri(VITRO_ITEM_API + "/" + itemId)
                .retrieve()
                .bodyToMono(Item.class);

    }

    public Mono<Item> placeItemToCartOfUser(
            Long userId, Long itemId,
            String action) {
        if (ActionType.PLUS.name().equalsIgnoreCase(action)) {
            Mono<Cart> cartMono = webClient.get()
                    .uri(VITRO_CART_API + "/create/" + userId + "/" + itemId)
                    .retrieve().bodyToMono(Cart.class);
            return cartMono.map(c -> itemById(itemId)).flatMap(i -> i);
        } else if (ActionType.MINUS.name().equalsIgnoreCase(action)) {
            Mono<Item> cartMono = webClient.get()
                    .uri(VITRO_CART_API + "/remove/" + userId + "/" + itemId)
                    .retrieve().bodyToMono(Item.class);
            return cartMono.map(c -> itemById(itemId)).flatMap(i -> i);
        }
        return Mono.just(new Item());
    }

}
