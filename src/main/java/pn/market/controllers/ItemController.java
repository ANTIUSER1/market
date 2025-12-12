package pn.market.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.result.view.Rendering;
import pn.market.additional.ActionType;
import pn.market.additional.Paging;
import pn.market.entities.Item;
import pn.market.services.ModelService;
import pn.market.services.impl.ItemServiceImpl;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Controller
@RequestMapping("/")
public class ItemController {

    @Autowired
    private ItemServiceImpl itemService;

    @Autowired
    private ModelService modelService;

    @Autowired
    private DatabaseClient databaseClient;

    @GetMapping("/i-all")
    public Mono<Rendering> iAllI(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "2") int pageSize,
            @RequestParam(value = "search", required = false, defaultValue = " ") String search,
            @RequestParam(value = "sorted", required = false, defaultValue = "ALPHA") String sorted
    ) {
        Flux<Item> itemFlux = itemService.findAll();
        Mono<Pageable> pageableMono = modelService.createPageble(page, pageSize, sorted);
        Mono<Paging> pageMono = itemService.findAllAndPaging(pageableMono);

        Mono<Rendering> r = Mono.just(Rendering.view("__items")
                .modelAttribute("items", itemFlux)
                .modelAttribute("paging", pageMono)
                .modelAttribute("page", pageableMono)
                .build());
        return r;
    }


    @GetMapping("/i-all/{id}")
    public Mono<Rendering> iById(
            @PathVariable("id") Long id,
            @RequestParam(value = "action", required = false) String action
    ) {

        Mono<Item> itemMono = itemService.findById(id);
        Mono<Long> cartIdMono = itemService.getItemCartId(itemMono);
        itemMono = Mono.zip(itemMono, cartIdMono).map(t -> {
            Item i = t.getT1();
            long cartId = t.getT2();
            Mono<Item> mi = null;
            if (action != null) {
                if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) {
                    System.out.println("i --b = " + i);
                    mi = itemService.plusForMono(i, cartId);


                    System.out.println("i --a  = " + i);
                }

                if (ActionType.MINUS.name().equalsIgnoreCase(action.trim())) {
                    System.out.println("i --b = " + i);
                    mi = itemService.minusForMono(i);
                    System.out.println("i --a  = " + i);
                }
            }
            if (mi != null) return mi;
            else return Mono.just(i);
        }).flatMap(i -> i);


        //}

        /*
        Mono<Item> itemMono = itemService.findById(id)
                .map(
                        i -> {
                            Mono<Item> mi = null;
                            if (action != null) {
                                if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) {
                                    System.out.println("i --b = " + i);
                                    mi = itemService.plusForMono(i, 1L);


                                    System.out.println("i --a  = " + i);
                                }

                                if (ActionType.MINUS.name().equalsIgnoreCase(action.trim())) {
                                    System.out.println("i --b = " + i);
                                    mi = itemService.minusForMono(i);
                                    System.out.println("i --a  = " + i);
                                }
                            }
                            if (mi != null) return mi;
                            else return Mono.just(i);
                        }).flatMap(i -> i);


        */


               /*
        Mono<Item> itemMono = itemService.findById(id);
                .map(
                        i -> {
                            if (action != null) {
                                if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) {
                                    System.out.println("i --b = " + i);
                                    itemService.plusForMono(i, 1L);


                                    System.out.println("i --a  = " + i);
                                }

                                if (ActionType.MINUS.name().equalsIgnoreCase(action.trim())) {
                                    System.out.println("i --b = " + i);
                                    itemService.minusForMono(i);
                                    System.out.println("i --a  = " + i);
                                }
                            }

                            return i;
                        });
                */
        //Mono<Long> cartIdMono = itemService.getItemCartId(itemMono);
        /*
        itemMono = Mono.zip(itemMono, cartIdMono).map(t -> {

            Item i = t.getT1();
            long cartId = t.getT2();

            if (action != null) {
                if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) {
                    System.out.println("i ++b = " + i);
                    itemService.plusForMono(i, cartId);
                    System.out.println("i ++a  = " + i);
                }
                if (ActionType.MINUS.name().equalsIgnoreCase(action.trim())) {
                    System.out.println("i --b = " + i);
                    itemService.minusForMono(i);
                    System.out.println("i --a  = " + i);
                }
            }

            return i;
        });

         */
        Mono<Rendering> r = Mono.just(Rendering.view("item")
                .modelAttribute("item", itemMono)
                .modelAttribute("action", action)
                .build());
        return r;
    }

    @GetMapping("/all/{id}")
    public Mono<Rendering> getAllItems(@PathVariable Long id) {
        Mono<Item> itemFlux = itemService.findById(id);

        Rendering r = Rendering.view("all")
                .modelAttribute("items", itemFlux)
                .build();

        return Mono.just(r);


    }

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
            if (ActionType.MINUS.name().equalsIgnoreCase(action.trim()))
                item = itemService.minus(itemOptional.get());

            if (ActionType.PLUS.name().equalsIgnoreCase(action.trim())) {
                if (itemOptional.get().getCartId() != null) {
                    item = itemService.plus(itemOptional.get(), itemOptional.get().getCartId());
                }
            }
            if (item != null) model.addAttribute("item", item);
            else {
                model.addAttribute("errorMSG", "INCORRECT ITEM REQUEST : ITEM  " + id + " IS NOT IN CART");
                return "items";
            }
            model.addAttribute("action", action);
            model.addAttribute("item", item);

            return "item";
        }
        return "items";

    }
}
