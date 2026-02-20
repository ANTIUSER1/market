package pn.market.market_entities.forWEB;

//import jakarta.persistence.*;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Table(name = "carts", schema = "market")
public class Cart implements Comparable<Cart> {


    @Id
    @Getter
    private Long id;


    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public int compareTo(Cart o) {
        return (int) (this.id - o.id);
    }


}
