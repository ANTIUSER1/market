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

    @Autowired
    private String authHost;

    //    /api/vitro/items/add-cart/{cartID}/{action}
    @Override
    public Mono<Item> placeItemToCart(
            long itemId,
            String action) {

        Mono<Item> itemMono = webClient.get()
                .uri(authHost + "/api/vitro/items/i/" + itemId)
                .retrieve()
                .bodyToMono(Item.class);
        System.out.println("MONO-ACTION CREATED ");
        itemMono = Mono.zip(itemMono,
                        this.createCartForItemIfNotExists(itemMono, itemId)
                )
                .map(t -> {
                    Item i = t.getT1();
                    Long cartId = t.getT2();
                    if (action != null) {
                        return webClient.put()
                                .uri(authHost + "/api/vitro/items/add-cart/"
                                        + cartId + "/" + action)
                                .bodyValue(i)
                                .retrieve().bodyToMono(Item.class);
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


}
