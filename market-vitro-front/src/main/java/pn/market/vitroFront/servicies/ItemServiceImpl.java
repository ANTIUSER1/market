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

import static pn.market.vitroFront.config.AuthPaths.VITRO_CART_API;
import static pn.market.vitroFront.config.AuthPaths.VITRO_ITEM_API;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private CartServiceImpl cartService;


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
        return webClient.get()
                .uri(VITRO_CART_API + "/total-sum-of-cart/" + cartId)
                .retrieve().bodyToMono(Long.class);
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

    @Override
    public Mono<Paging> findAllAndPaging(Mono<Pageable> pageable) {
        return null;
    }

    @Override
    public Mono<Paging> findAllAndPagingWithFluxItem(
            Mono<Pageable> pageable, Flux<Item> itemFlux) {
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

    @Override
    public void removeFromOrder(long orderId) {
    }

    public Flux<Item> itemOfUser(Long userId) {
        return webClient.get()
                .uri(VITRO_ITEM_API + "/get-cart-of-user/" + userId)
                .retrieve().bodyToFlux(Item.class);
    }

    public Mono<Long> getTotalOfSum(Long userId) {
        return webClient.get()
                .uri(VITRO_ITEM_API + "/get-total-sum-cart-of-user/" + userId)
                .retrieve().bodyToMono(Long.class);
    }
}
