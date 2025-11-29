package pn.market.controllers;

import org.hibernate.annotations.AttributeAccessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.*;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pn.market.additional.Paging;
import pn.market.additional.SortType;
import pn.market.entities.Item;
import pn.market.services.ModelService;
import pn.market.services.impl.ItemServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest({ItemController.class })
@Import(  {ItemServiceImpl.class, ModelService.class})
//@SpringBootTest
//@AutoConfigureMockMvc
class ItemControllerTest {

    @MockitoBean
    private ItemServiceImpl itemService;

    @MockitoBean
    private ModelService modelService;

    @Autowired
    private MockMvc mvc;

    private List<Item> items;

private  Item item;

    @BeforeEach
    void setUp() {
        items = new ArrayList<>();
       Item item = new Item();
       item.setPrice(100L);
       item.setId(100L);
       item.setTitle("test");
       item.setDescription("test-d");
       item.setImgPath("p");
       item.setCount(100);
        items.add( item );
       // item=new Item();

    }

    @Test
    void itemsIndex() throws Exception {
    Pageable pageable = PageRequest.of(0, 2,
            Sort.Direction.ASC, "title");
         when(itemService.findAllAndPaging( any() ))
                 .thenReturn(new PageImpl<>(items, pageable, 2));
//                .thenReturn(new PageImpl<>(items, pageable, 2));
        assertTrue(itemService.findAllAndPaging(pageable).getTotalPages() == 1);

        mvc.perform(MockMvcRequestBuilders.get("/")
                        .param("page", "0")
                        .param("pageSize", "2")
                        .param("search", "")
                        .param("sorted", "ALPHA")
                )


                .andExpect(model().attribute("items", items))
                .andExpect(model().attribute("page", 0))
                .andExpect(model().attribute("pageSize", 2))
                .andExpect(model().attribute("paging",
                                new Paging(2, 0, true, false))
                )

                    .andExpect(view().name("items"))
                    .andExpect(status().isOk()
                )
        ;

/*
  model.addAttribute("items", items.get().toList());
        model.addAttribute("page", page);
        model.addAttribute("paging",
                new Paging(pageSize, page,
                        items.hasNext(), items.hasPrevious()));
 */
    }

    @Test
    void items() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/items")
                        .param("page", "0")
                        .param("pageSize", "2")
                        .param("search", "")
                        .param("sorted", "ALPHA")
                )
                .andExpect(view().name("items"))
                .andExpect(status().isOk()
                )
        ;
    }

    @Test
    void item() throws Exception {

        Pageable pageable = Pageable.ofSize(2);
        when(itemService.getById(1L)).thenReturn(Optional.ofNullable(item));
        assertTrue(itemService.getById(1L).isPresent());
        mvc.perform(MockMvcRequestBuilders.get("/items/{id}", 1)
                        .param("id", "1")
                        .param("action", "false")
                )
                .andExpect(status().isOk());

           }

}