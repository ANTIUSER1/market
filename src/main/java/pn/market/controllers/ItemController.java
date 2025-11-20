package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pn.market.additional.Paging;
import pn.market.additional.SORT_TYPE;
import pn.market.entities.Item;
import pn.market.services.impl.ItemServiceImpl;

@Controller
@RequestMapping("/")
public class ItemController {
@Autowired
private ItemServiceImpl itemService;
/*
    @GetMapping
    public String index( Model model ) {
        System.out.println("------------asd -----------------");
        return "asd";
    }

 */

    @GetMapping
    public String itemsIndex(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String  sorted,
            Model model
    ) {
        model = createModel(page, pageSize, search, sorted, model);
        return "items";
    }

    @GetMapping("/items")
    public String items(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String  sorted,
            Model model
    ) {
        model = createModel(page, pageSize, search, sorted, model);
        return "items";
    }

    private Model createModel(int page, int pageSize, String search, String sorted, Model model) {
        Pageable pageable = createPageble(page, pageSize, sorted);
        Page<Item> items = itemService.findAllAndPaging(pageable);
        model.addAttribute("items", items.get().toList());
        model.addAttribute("page", page);
        model.addAttribute("paging",
                new Paging(pageSize, page,
                        items.hasNext(), items.hasPrevious()));
        return model;
    }

    private Pageable createPageble(int page, int pageSize, String sorted) {
        Pageable pageable = PageRequest.of(page, 10);
        if (SORT_TYPE.ALPHA.name().equals(sorted)) {
            pageable = PageRequest.of(page, pageSize,
                    Sort.Direction.ASC, "title");
        }
        if (SORT_TYPE.PRICE.name().equals(sorted)) {
            pageable = PageRequest.of(page, pageSize,
                    Sort.Direction.ASC, "price");
        }
        return pageable;
    }

}
