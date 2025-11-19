package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pn.market.entities.Item;
import pn.market.services.impl.ItemServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/")
public class ItemController {
@Autowired
private ItemServiceImpl itemService;
    @GetMapping
    public String index( Model model ) {
        System.out.println("------------asd -----------------");
        return "asd";
    }

    @GetMapping("/items")
    public String items(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "NO") String  sorted,
            Model model
    ) {
        List<Item> items = itemService.findAll(page, pageSize, search, sorted);
        System.out.println(items);
        System.out.println(items.size());
        System.out.println( "Page: " + page + " PageSize: " + pageSize + " Search: " + search + " Sorted: " + sorted + " ");
        model.addAttribute("items", items);
        model.addAttribute("page", page);
        model.addAttribute("pageSize", pageSize);

        return "items";
    }

}
