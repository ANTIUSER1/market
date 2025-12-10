package pn.market.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pn.market.additional.ActionType;
import pn.market.entities.Cart;
import pn.market.entities.Item;
import pn.market.services.impl.CartServiceImpl;
import pn.market.services.impl.ItemServiceImpl;

import java.util.List;

@Controller
@RequestMapping("/cart")

public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    @Autowired
    private ItemServiceImpl itemService;

    @GetMapping("/items")
    public String addItem(
            @RequestParam("itemId") Long itemId,
            @RequestParam("action") String action,
            Model model) {
        Cart cart = null;

        if (action != null && itemId != null) {
            if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) cart = cartService.plusItem(itemId);
            if (ActionType.MINUS.name().equals(action.trim())) cart = cartService.minusItem(itemId);

            if (cart != null) {
                List<Item> items = itemService.getItemsByCartId(cart.getId());
                long total = itemService.getTotalSum(items);
                model.addAttribute("total", total);
                model.addAttribute("items", items);
                return "cart";
            }
        }    return "items";
    }
}
