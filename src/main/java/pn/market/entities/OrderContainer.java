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
        final StringBuffer sb = new StringBuffer("OrderContainer{");
        sb.append("order=").append(order);
        sb.append(", items=").append(items);
        sb.append('}');
        return sb.toString();
    }

    public long totalSumm(){
     totalSSS();
        return  items.stream().mapToLong(i-> i.getPrice()*i.getCount()).sum()  ;
    }

    public void totalSSS(){
      for(Item i:items) {
          System.out.println(
                  i.getTitle() + "   " +
                  i.getCount()+ "   "+i.getPrice()+"     ::::::    "+i.getPrice()*i.getCount());
      }
    }

}
