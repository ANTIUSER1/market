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
@Table(name = "carts_items", schema = "market")
public class CartItems {


    @Getter
    Long cartId;
    @Getter
    Long itemId;
    @Id
    @Getter
    private Long id;


    public CartItems(Long itemId, Long cartId) {
        this.itemId = itemId;
        this.cartId = cartId;
    }

}
