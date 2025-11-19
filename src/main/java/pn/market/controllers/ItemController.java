package pn.market.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ItemController {

    @GetMapping
    public String index( Model model ) {
        System.out.println("------------asd -----------------");
        return "asd";
    }

}
