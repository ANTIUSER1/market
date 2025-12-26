package pn.market.rest;

//import jakarta.servlet.ServletContext;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")

public class ItemRest {

//    @Autowired
//    private ItemServiceImpl itemService;

//    @GetMapping("/setof/{count}")
//    public List<Item> createSet(@PathVariable("count") int count) {
//        List<Item> items = new ArrayList<>();
//        for (int i = 0; i < count; i++) {
//            Item item = new Item();
//            item.setTitle("Item " + i);
//            item.setDescription("Description " + i);
//            item.setCount((int) (100 * Math.random()));
//            item.setImgPath(i + ".jpg");
//            item.setPrice((long) (i + 10000 * Math.random()));
//            items.add(item);
//        }
//        items = itemRepo.saveAll(items);
//        return items;
//    }

//    @PutMapping("/img/{id}")
//    public ResponseEntity<?> loadItemImage(
//            @PathVariable("id") Long id, @RequestParam("file") MultipartFile file
//    ) throws IOException {
////        if (file != null) {
////          //  Item item = itemService.uploadFile(file, id);
////            if (item != null)
////                return ResponseEntity.ok(item);
////        }
//        return ResponseEntity.badRequest().build();
//    }
}
