package pn.market.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pn.market.additional.ActionType;
import pn.market.additional.Paging;
import pn.market.additional.SortType;
import pn.market.entities.Item;
import pn.market.services.impl.ItemServiceImpl;

import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
public class ItemController {
@Autowired
private ItemServiceImpl itemService;



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

    @GetMapping("/items/{id}")
    public String item(
            @PathVariable("id") Long id,
            @RequestParam(value = "action", required = false) String action,
            Model model) {
        Item item = null;
        Optional<Item> itemOptional = itemService.findById(id);
        if (action != null && itemOptional.isPresent()) {
            System.out.println(action);
            System.out.println("    (ActionType.MINUS.equals(action)) "+ (ActionType.MINUS.name().equals(action)));
            System.out.println("    \t     ActionType.MINUS.equals(action.trim()     "+ (ActionType.MINUS.name().equals(action.trim() )));
            System.out.println("    \t     ActionType.PLUS.equals(action.trim()     "+ (ActionType.PLUS.name().equals(action.trim() )));
            System.out.println("    \t \t\t    ActionType.PLUS    "+  ActionType.PLUS );
            System.out.println("    \t \t\t    ActionType.MINUS    "+  ActionType.MINUS);
            if (ActionType.MINUS.name().equals(action.trim())) item = itemService.minus(itemOptional.get());
            else if (ActionType.PLUS.name().equals(action.trim()))  item = itemService.plus(itemOptional.get());
            if(item!=null)model.addAttribute("item", item );
            else {
                log.error("Item of {} not found, or action not set", id);
                return "items";
            }
            return "item";
        }
        log.error("Item of {} not found, or action not set", id);
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
        if (SortType.ALPHA.name().equals(sorted)) {
            pageable = PageRequest.of(page, pageSize,
                    Sort.Direction.ASC, "title");
        }
        if (SortType.PRICE.name().equals(sorted)) {
            pageable = PageRequest.of(page, pageSize,
                    Sort.Direction.ASC, "price");
        }
        return pageable;
    }

}
