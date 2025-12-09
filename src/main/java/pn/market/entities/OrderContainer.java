package pn.market.entities;

import java.util.List;

public class OrderContainer {

    private Order order;
    private List<Item> items;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> itemList) {
        this.items = itemList;
    }

    @Override
    public String toString() {
        String sb = "OrderContainer{" + "order=" + order +
                ", items=" + items +
                '}';
        return sb;
    }

    public long totalSumm() {
        return items.stream().mapToLong(i -> i.getPrice() * i.getCount()).sum();
    }
}
