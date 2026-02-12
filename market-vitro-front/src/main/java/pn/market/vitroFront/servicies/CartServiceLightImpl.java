package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Mono;

@Service
public class CartServiceLightImpl implements CartServiceLight {


    @Autowired
    private WebClient webClient;

    @Override
    public Mono<Item> placeItemToCart(
            long itemId,
            String action) {
        System.out.println(
                "PLACE ITEM TO CART " + itemId
                        + "   ACTION  " + action

        );
        Mono<Item> itemMono = webClient.get()
                .uri("http://localhost:8521/api/vitro/items/i/" + itemId)
                .retrieve()
                .bodyToMono(Item.class);
        System.out.println("MONO-ACTION CREATED ");
        itemMono = Mono.zip(itemMono,
                        this.createCartForItemIfNotExists(itemMono, itemId)
                )
                .map(t -> {

                    Item i = t.getT1();
                    Long cartId = t.getT2();
                    System.out.println("MONO-ACTION MAP CART_ID " + cartId);

                    if (action != null) {
                        Mono<Item> min = webClient.put()
                                .uri("http://localhost:8521/api/vitro/items/"
                                        + cartId + "/" + action)
                                .bodyValue(i)
                                .retrieve().bodyToMono(Item.class);
                        System.out.println("NOT=NULL=ACTION "
                                + action + "   RETURN VALUE " + min);
                        return min;
                    }
                    return Mono.just(i);
                })
                .flatMap(i -> i);
        return itemMono;
    }

    public Mono<Long> createCartForItemIfNotExists(
            Mono<Item> itemMono,
            long itemId) {
        Mono<Long> cartIdMono = itemMono
                .map(i -> {
                    if (i.getCartId() == null) {
                        return null;//this.createNewCart();
                    } else return Mono.just(i.getCartId());
                }).flatMap(ci -> ci);
        return cartIdMono;
    }
/*
    public Mono<Item> placeItemToCart(
            Mono<Item> itemMono,
            long itemId,
            String action) {
       itemMono = Mono.zip(
                        itemMono,
                        this.createCartForItemIfNotExists(itemId)

                )
                .map(t -> {

                    Item i = t.getT1();
                    Long cartId = t.getT2();


                    if (action != null) {
                        return itemService.addToCart(i, cartId, action);
                    }
                    return Mono.just(i);
                })
                .flatMap(i -> i);
        return itemMono;
    }

 */

}
