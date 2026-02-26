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
        System.out.println("::::::::---ITEM BY ID---:::::::::::");
        return webClient.get()
                .uri(VITRO_ITEM_API + "/" + itemId)
                .retrieve()
                .bodyToMono(Item.class);

    }
/*
    public Mono<Item> placeItemToCart(
            Long itemId,
            String action) {
        System.out.println(".........PLACE!!!  " + itemId + "    " + action);
        System.out.println(".........ITEM GET FROM    " + "/api/vitro/items/i/" + itemId);
        Mono<Item> itemMono = itemById(itemId)
                .map(i -> {
                            if (i.getCartId() == null) {
                                webClient.get()
                                        .uri(VITRO_CART_API + "/create/" + itemId)
                                        .retrieve().bodyToMono(Cart.class)
                                        .map(cc -> {
                                                    System.out.println("    ^^^^^   CCC " + cc.getId());
                                                    i.setCartId(cc.getId());
                                                    return cc;
                                                }
                                        )
                                        .subscribe();
                            }
                            System.out.println("   ::::IIIIIII--CID:: " + i.getCartId());
                            return i;
                        }
                );
        itemMono.subscribe(i -> System.out.println("  RE-REQUEST III \n" + i));
        itemMono.subscribe(imm -> System.out.println("----IMM CART " + imm.getCartId()));
        itemMono.subscribe(imm -> System.out.println("----IMM ID " + imm.getId()));
        System.out.println("000000000000000000000000");
        itemMono = addItemToCart(itemMono, itemId, action);
        return itemMono;
    }
    */

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

    private Mono<Item> addItemToCart(Mono<Item> itemMono, Long itemId, String action) {
/*
        System.out.println("     _____00000-itemId- " + itemId);
        System.out.println("     _____00000-action- " + action);
        return Mono.zip(itemMono, longMono
                        // this.createCartForItemIfNotExists(itemMono, itemId)
                )
                .map(t -> {
                    System.out.println("   IN MAP RUN@");
                    Item i = t.getT1();
                    Long cartId = t.getT2();
                    System.out.println("    ---CART-ID::::: " + cartId);
                    if (action != null && cartId != null) {
                        return webClient.get()
                                .uri(VITRO_ITEM_API + "/add-cart/"
                                        + i.getId() + "/" + cartId + "/" + action)
                                .retrieve().bodyToMono(Item.class);
                    }
                    return Mono.just(i);
                })
                .flatMap(i -> i);
*/
        return Mono.just(new Item());
    }

}
