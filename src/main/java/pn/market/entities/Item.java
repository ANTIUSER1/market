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



}
