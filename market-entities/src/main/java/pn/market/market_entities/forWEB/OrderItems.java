package pn.market.market_entities.forWEB;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@ToString
@NoArgsConstructor
@Table(name = "orders_items", schema = "market")
public class OrderItems implements Comparable<OrderItems> {


    @Getter
    Long orderId;
    @Getter
    Long itemId;
    @Id
    @Getter
    private Long id;


    public OrderItems(Long itemId, Long orderId) {
        this.itemId = itemId;
        this.orderId = orderId;
    }

    @Override
    public int compareTo(OrderItems o) {
        return (int) (this.id - o.id);
    }
}
