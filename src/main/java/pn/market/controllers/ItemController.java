package pn.market.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pn.market.additional.ActionType;
import pn.market.entities.Item;
import pn.market.services.ModelService;
import pn.market.services.impl.ItemServiceImpl;

import java.util.Optional;

@Controller
@RequestMapping("/")
@Slf4j
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private ModelService modelService;

    @GetMapping
    public String itemsIndex(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted,
            Model model
    ) {
        model = modelService.createModel(page, pageSize, search, sorted, model).block();
        return "items";
    }

    @GetMapping("/items")
    public String items(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "3") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted,
            Model model
    ) {
        model = modelService.createModel(page, pageSize, search, sorted, model).block();
        return "items";
    }

    @GetMapping("/items/{id}")
    public String item(
            @PathVariable("id") Long id,
            @RequestParam(value = "action", required = false) String action,
            Model model) {
        Item item = null;
        Optional<Item> itemOptional = itemService.getById(id);


        if (action != null && itemOptional.isPresent()) {
            if (ActionType.MINUS.name().equalsIgnoreCase(action.trim())) item = itemService.minus(itemOptional.get());
            else if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())
                    && itemOptional.get().getCartId() != null)
                item = itemService.plus(itemOptional.get(), itemOptional.get().getCartId());
            if (item != null) model.addAttribute("item", item);
            else {
                return "items";
            }
            model.addAttribute("action", action);
            model.addAttribute("item", item);

            return "item";
        }
        return "items";
    }
}
