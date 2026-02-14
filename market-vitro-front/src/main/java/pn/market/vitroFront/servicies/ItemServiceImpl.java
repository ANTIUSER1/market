package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pn.market.market_entities.Paging;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {


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
    private WebClient webClient;

    @Override
    public Flux<Item> findAll() {
        return null;
    }

    @Override
    public Mono<Item> getById(Long id) {
        return null;
    }

    @Override
    public Mono<Long> getTotalSum(Long cartId) {
        return webClient.get()
                .uri("/api/vitro/items/get-total-sum/" + cartId)
                .retrieve().bodyToMono(Long.class);
    }


    @Override
    public Flux<Item> getItemsByCartDataFromMonoToFlux(Long cartId) {
        return webClient.get()
                .uri("/api/vitro/items/get-by-cart/" + cartId)
                .retrieve()
                .bodyToFlux(Item.class);
    }

    public Flux<Item> getItemsByCartDataFromMonoToFlux1(long cartId) {
        return webClient.get()
                .uri("/api/vitro/items/i/" + cartId)
                .retrieve()
                .bodyToFlux(Item.class)
                .map(i -> {
                    return webClient.put()
                            .uri("/api/vitro/items/get-items-by-cart")
                            .bodyValue(i)
                            .retrieve().bodyToFlux(Item.class);
                })
                .flatMap(f -> f);
    }

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
}
