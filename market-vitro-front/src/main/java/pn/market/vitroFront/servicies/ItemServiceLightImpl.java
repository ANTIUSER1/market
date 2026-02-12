package pn.market.vitroFront.servicies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.additional.Paging;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class ItemServiceLightImpl implements ItemServiceLight {


    private int pageSize;
    private int offset;
    @Value("${spring.web.resources.static-locations}")
    private String imgPath;
//    @Autowired
//    private FileServiceImpl fileService;
//    @Autowired
//    private ItemRepo itemRepo;
//    @Autowired
//    private OrderRepo orderRepo;

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
}
