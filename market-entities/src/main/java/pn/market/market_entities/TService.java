package pn.market.market_entities;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TService<T> {


    Flux<T> findAll();

    Mono<T> getById(Long id);

    Mono<Paging> findAllAndPaging(Mono<Pageable> pageable);

}
