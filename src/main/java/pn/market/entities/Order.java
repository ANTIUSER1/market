package pn.market.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long totalSum;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Item> itemList;

}
