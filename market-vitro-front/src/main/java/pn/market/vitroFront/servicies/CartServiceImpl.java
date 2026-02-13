package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Cart;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CartServiceImpl implements TService<Cart> {


    @Autowired
    private WebClient webClient;

    @Autowired
    private String authHost;

    //    /api/vitro/items/add-cart/{cartID}/{action}

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

    public Mono<Item> placeItemToCart(
            long itemId,
            String action) {
        System.out.println(".........PLACE!!!  " + itemId + "    " + action);
        System.out.println(".........ITEM GET FROM    " + authHost + "/api/vitro/items/i/" + itemId);
        Mono<Item> itemMono = webClient.get()
                .uri(authHost + "/api/vitro/items/i/" + itemId)
                .retrieve()

                .bodyToMono(Item.class);

        System.out.println(".........MONO_ITEM CREATED " + itemId + "    " + action);
        // itemMono.subscribe(i -> System.out.println("    :::: " + i));


        itemMono = Mono.zip(itemMono,
                        this.createCartForItemIfNotExists(itemMono, itemId)
                )
                .map(t -> {
                    Item i = t.getT1();
                    Long cartId = t.getT2();
                    System.out.println("    ITEM_VALUE " + i);
                    if (action != null) {
                        return webClient.put()
                                .uri(authHost + "/api/vitro/items/add-cart/"
                                        + cartId + "/" + action)
                                .bodyValue(i)
                                .retrieve().bodyToMono(Item.class);
                    }

                    System.out.println("******   III " + i);
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
