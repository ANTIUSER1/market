package pn.market.vitroFront.servicies;

import org.springframework.data.domain.Pageable;
import pn.market.market_entities.Paging;
import pn.market.market_entities.TService;
import pn.market.market_entities.forWEB.Item;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ItemService extends TService<Item> {

    Mono<Paging> findAllAndPagingWithFluxItem(Mono<Pageable> pageableMono, Flux<Item> itemFlux);
}
