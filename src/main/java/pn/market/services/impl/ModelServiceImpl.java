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

@Service
public class ModelServiceImpl implements ModelService {

    @Autowired
    private ItemServiceImpl itemService;

    @Override
    public Model createModel(int page, int pageSize, String search, String sorted, Model model) {
       Pageable pageable = createPageble(page, pageSize, sorted);
        itemService.findAllAndPaging(pageable);

//        Page<Item> items = itemService.findAllAndPaging(pageable);
//        model.addAttribute("items", items.get().toList());
//        model.addAttribute("page", page);
//        model.addAttribute("paging",
//                new Paging(pageSize, page,
//                        items.hasNext(), items.hasPrevious()));
        return null;
    }

    @Override
    public Pageable createPageble(int page, int pageSize, String sorted) {
        Pageable pageable = PageRequest.of(page, 10);
        if (SortType.ALPHA.name().equalsIgnoreCase(sorted)) {
            pageable = PageRequest.of(page, pageSize,
                    Sort.Direction.ASC, "title");
        }
        if (SortType.PRICE.name().equalsIgnoreCase(sorted)) {
            pageable = PageRequest.of(page, pageSize,
                    Sort.Direction.ASC, "price");
        }
        return pageable;
    }
}
