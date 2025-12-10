package pn.market.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pn.market.additional.Paging;
import pn.market.additional.SortType;
import pn.market.entities.Item;
import pn.market.services.ModelService;
import reactor.core.publisher.Mono;

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ItemServiceImpl itemService;

    private boolean hasPrev=false;
    private boolean hasNext=true;
    @Override
    public Mono<Model> createModel(int page, int pageSize, String search, String sorted, Model model) {

        Mono<Pageable>  pageable = createPageble(page, pageSize, sorted);
        Mono<Page<Item>> items = itemService.findAllAndPaging(pageable);
        model.addAttribute("items", items );
        model.addAttribute("page", page);
       model.addAttribute("paging",
                new Paging(pageSize, page,hasNext, hasPrev ));
        return Mono.just(model);
    }

    @Override
    public Mono<Pageable> createPageble(int page, int pageSize, String sorted) {
        Mono<Pageable> result=
                Mono.just(PageRequest.of(page, pageSize))
                        .map(p->{
                            hasPrev= p.hasPrevious();
                            hasNext=p.next()!=null;
                            return p;
                        })
                        .map(p->{
                            if (SortType.ALPHA.name().equalsIgnoreCase(sorted)) {
                                p = PageRequest.of(page, pageSize,
                                        Sort.Direction.ASC, "price");
                            }
                            return p;})
                        .map(p->{
                            if (SortType.PRICE.name().equalsIgnoreCase(sorted)) {
                                p = PageRequest.of(page, pageSize,
                                        Sort.Direction.ASC, "price");
                            }
                            return p;});
        return result;
 }
}
