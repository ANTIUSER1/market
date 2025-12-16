package pn.market.entities;

import reactor.core.publisher.Flux;

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

    @Override
    public String toString() {
        String sb = "OrderContainer{" + "order=" + order +
                '}';
        return sb;
    }


}
