package pn.market.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Long price;
    private Integer count;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cart cart;


}
