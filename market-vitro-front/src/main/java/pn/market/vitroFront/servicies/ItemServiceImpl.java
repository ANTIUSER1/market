package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.Paging;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static pn.market.vitroFront.config.AuthPaths.VITRO_CART_API;
import static pn.market.vitroFront.config.AuthPaths.VITRO_ITEM_API;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CartServiceImpl cartService;

//    @Autowired
//    private FileServiceImpl fileService;
//    @Autowired
//    private ItemRepo itemRepo;
//    @Autowired
//    private OrderRepo orderRepo;
/*
 @Override
    public Mono<Paging> findAllAndPaging(
            Mono<Pageable> pageable,
            Flux<Item> itemsFlux
    ) {

        Mono<Pageable> pageableMono = pageable.map(p -> {
                    pageSize = p.getPageSize();
                    offset = p.getPageNumber() * pageSize;
                    return p;
                }
        );
        itemsFlux = itemsFlux
                .skip(offset)
                .take(pageSize);
        Mono<Long> countMono = itemsFlux.count();

        return Mono.zip(itemsFlux.collectList(), countMono, pageable)
                .map(tuple -> {
                    List<Item> items = tuple.getT1();
                    long total = tuple.getT2();
                    Pageable p = tuple.getT3();
                    boolean hasPrevious = p.hasPrevious();
                    boolean hasNext = p.next() == null;

                    Paging paging = new Paging(
                            p.getPageSize(), p.getPageNumber(),
                            (int) (total / p.getPageSize() + 1),
                            hasNext, hasPrevious);
                    return paging;
                });

    }

    @Override
    public Flux<Item> getItemsByCartDataFromMonoToFlux(Item item) {
        return webClient.put()
                .uri(authHost + "/api/get-items-by-cart")
                .bodyValue(item)
                .retrieve().bodyToFlux(Item.class);
    }

    @Override
    public Mono<Long> getTotalSum(Flux<Item> itemsFlux) {
        return webClient.put()
                .uri(authHost + "/api/get-total-sum")
                .bodyValue(itemsFlux)
                .retrieve().bodyToMono(Long.class);
    }
    */


    private int pageSize;
    private int offset;
    @Value("${spring.web.resources.static-locations}")
    private String imgPath;


    @Autowired
    @Qualifier("BACK")
    private WebClient webClient;

    @Override
    public Flux<Item> findAll() {
        return null;
    }

    @Override
    public Mono<Item> getById(Long id) {
        return webClient.get()
                .uri(VITRO_ITEM_API + "/" + id)
                .retrieve().bodyToMono(Item.class);

    }

    @Override
    public Mono<Long> getTotalSum(Long cartId) {
        System.out.println("----TOTAL CART SUMM " + cartId);
        return webClient.get()
                .uri(VITRO_CART_API + "/total-sum-of-cart/" + cartId)
                .retrieve().bodyToMono(Long.class);
    }

    @Override
    public Long getCartFromMonoItem(Mono<Item> itemMono) {
        AtomicReference<Long> cartId = new AtomicReference<>(0L);
        // itemMono.subscribe(i -> cartId.set(i.getCartId()));
        return cartId.get();
    }

    @Override
    public Flux<Item> getItemsByOrderId(Long id) {
        return webClient.get()
                .uri(VITRO_ITEM_API + "/by-order/" + id)
                .retrieve().bodyToFlux(Item.class);
    }


    @Override
    public Flux<Item> getItemsByCartDataFromMonoToFlux(Long cartId) {
        return webClient.get()
                .uri(VITRO_ITEM_API + "/get-items-by-cart/" + cartId)
                .retrieve().bodyToFlux(Item.class);
    }

    /*
        public Flux<Item> getItemsByCartDataFromMonoToFlux1(long cartId) {
            return webClient.get()
                    .uri(VITRO_ITEM_API+"/i/" + cartId)
                    .retrieve()
                    .bodyToFlux(Item.class)
                    .map(i -> {
                        return webClient.put()
                                .uri(VITRO_ITEM_API+"/get-items-by-cart")
                                .bodyValue(i)
                                .retrieve().bodyToFlux(Item.class);
                    })
                    .flatMap(f -> f);
        }
    */
    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return null;
    }

    @Override
    public Mono<Paging> findAllAndPagingWithFluxItem(Mono<Pageable> pageable,
                                                     Flux<Item> itemFlux) {

        Mono<Pageable> pageableMono = pageable.map(p -> {
                    pageSize = p.getPageSize();
                    offset = p.getPageNumber() * pageSize;
                    return p;
                }
        );
        itemFlux = itemFlux
                .skip(offset)
                .take(pageSize);
        Mono<Long> countMono = itemFlux.count();

        return Mono.zip(itemFlux.collectList(), countMono, pageable)
                .map(tuple -> {
                    List<Item> items = tuple.getT1();
                    long total = tuple.getT2();
                    Pageable p = tuple.getT3();
                    boolean hasPrevious = p.hasPrevious();
                    boolean hasNext = p.next() == null;

                    Paging paging = new Paging(
                            p.getPageSize(), p.getPageNumber(),
                            (int) (total / p.getPageSize() + 1),
                            hasNext, hasPrevious);
                    return paging;
                });


    }
/*
    @Override
    public void updateCartInfo(Long itemId, String action) {
        Mono<Item> itemMono = cartService.placeItemToCart(itemId, action);
        Long cartId = this.getCartFromMonoItem(itemMono);
        Flux<Item> itemsFlux = this.getItemsByCartDataFromMonoToFlux(cartId);
        Mono<Long> total = this.getTotalSum(cartId);
        Mono<Long> cartIdMono = itemMono.map(Item::getCartId);
        itemsFlux.subscribe();
        total.subscribe();
        cartIdMono.subscribe();
    }
*/

    @Override
    public void removeFromOrder(long orderId) {
    }

    public void addOrder(Item item, Long orderId) {

        /*
        long id = item.getId();
        System.out.println("    SAVING --- " + id);
        System.out.println("    SAVING --- " + item);
        webClient.get()
                .uri(VITRO_ITEM_API + "/addOrder/" + orderId + "/" + id)
                .retrieve().bodyToMono(Item.class)
//                .subscribe(
//                        i -> System.out.println("----SSII " + i.getOrderId())
//
//                );
  */
    }

    public Flux<Item> itemOfUser(Long userId) {
        return webClient.get()
                .uri(VITRO_ITEM_API + "/get-cart-of-user/" + userId)
                .retrieve().bodyToFlux(Item.class);
    }

    //   get-total-sum-cart-of-user

    public Mono<Long> getTotalOfSum(Long userId) {
        return webClient.get()
                .uri(VITRO_ITEM_API + "/get-total-sum-cart-of-user/" + userId)
                .retrieve().bodyToMono(Long.class);
    }
}
