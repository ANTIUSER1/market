package pn.market.entities;

import lombok.ToString;
import reactor.core.publisher.Flux;

@ToString
public class OrderContainer {

    private Order order;
    private Flux<Item> items;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Flux<Item> getItems() {
        return items;
    }

    public void setItems(Flux<Item> itemList) {
        this.items = itemList;
    }


}
