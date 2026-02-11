package pn.market.vitroBack.servicies.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pn.market.vitroBack.additional.SortType;
import pn.market.vitroBack.servicies.ModelService;
import reactor.core.publisher.Mono;

@Service
public class ModelServiceImpl implements ModelService {

    private boolean hasPrev = false;
    private boolean hasNext = true;

    @Override
    public Mono<Model> createModel(int page, int pageSize, String search, String sorted, Model model) {
        return null;
    }

    @Override
    public Mono<Pageable> createPageble(int page, int pageSize, String sorted) {
        Mono<Pageable> result =
                Mono.just(PageRequest.of(page, pageSize))
                        .map(p -> {
                            hasPrev = p.hasPrevious();
                            hasNext = p.next() != null;
                            return p;
                        })
                        .map(p -> {
                            if (SortType.ALPHA.name().equalsIgnoreCase(sorted)) {
                                p = PageRequest.of(page, pageSize,
                                        Sort.Direction.ASC, "price");
                            }
                            return p;
                        })
                        .map(p -> {
                            if (SortType.PRICE.name().equalsIgnoreCase(sorted)) {
                                p = PageRequest.of(page, pageSize,
                                        Sort.Direction.ASC, "price");
                            }
                            return p;
                        });
        return result;
    }
}
