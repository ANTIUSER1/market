package ru.yandex.practicum.mymarket.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private long totalSum;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<Item> itemList;

}
