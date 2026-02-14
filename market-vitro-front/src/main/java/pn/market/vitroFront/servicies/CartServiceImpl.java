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

import static pn.market.vitroFront.config.AuthPaths.VITRO_ITEM_API;

@Service
public class CartServiceImpl implements TService<Cart> {

    @Autowired
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

    public Mono<Item> placeItemToCart(
            Long itemId,
            String action) {
        System.out.println(".........PLACE!!!  " + itemId + "    " + action);
        System.out.println(".........ITEM GET FROM    " + "/api/vitro/items/i/" + itemId);
        Mono<Item> itemMono = itemById(itemId);
        itemMono.subscribe(imm -> System.out.println("----IMM CART " + imm.getCartId()));
        itemMono.subscribe(imm -> System.out.println("----IMM ID " + imm.getId()));
        System.out.println("000000000000000000000000");
//        Mono<Item> itemMono1 = itemMono.map(i -> {
//            System.out.println("   GOT ITEM    " + i);
//            return i;
//        });
        itemMono = addItemToCart(itemMono, itemId, action);
        return itemMono;
    }

    public Mono<Long> createCartForItemIfNotExists(
            Mono<Item> itemMono,
            long itemId) {
        System.out.println("   /////CREATECART");
        Mono<Long> cartIdMono = itemMono
                .map(i -> {
                    System.out.println("          OOO----IIII " + i);
                    System.out.println("          OOO----CREATE BEGIN---cart-id " + i.getCartId());
                    if (i.getCartId() == null) {
                        return null;//this.createNewCart();
                    } else return Mono.just(i.getCartId());
                }).flatMap(ci -> ci);
        return cartIdMono;
    }

    private Mono<Item> addItemToCart(Mono<Item> itemMono, Long itemId, String action) {
        System.out.println("     _____00000-itemId- " + itemId);
        System.out.println("     _____00000-action- " + action);

        return Mono.zip(itemMono,
                        this.createCartForItemIfNotExists(itemMono, itemId)
                )
                .map(t -> {
                    System.out.println("   IN MAP RUN@");
                    Item i = t.getT1();
                    Long cartId = t.getT2();
                    if (action != null && cartId != null) {
                        return webClient.get()
                                .uri("/api/vitro/items/add-cart/"
                                        + i.getId() + "/" + cartId + "/" + action)
                                .retrieve().bodyToMono(Item.class);
                    }
                    return Mono.just(i);
                })
                .flatMap(i -> i);

    }

}
