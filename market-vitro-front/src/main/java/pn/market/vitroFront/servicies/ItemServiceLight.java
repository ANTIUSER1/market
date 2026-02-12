package pn.market.vitroFront.servicies;

import org.springframework.data.domain.Pageable;
import pn.market.market_entities.forWEB.Item;
import pn.market.vitroFront.additional.Paging;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ItemServiceLight {

    Mono<Paging> findAllAndPaging(
            Mono<Pageable> pageable,
            Flux<Item> itemsFlux);

}
