package pn.market.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data

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

    @Transient
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Order order;

//    @Transient
//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private Cart cart;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append("count=").append(count);
        sb.append(", price=").append(price);
        sb.append(", imgPath='").append(imgPath).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
