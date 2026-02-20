package pn.market.market_entities.forWEB;

//import jakarta.persistence.*;


import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@ToString
@Table(name = "items", schema = "market")
public class Item implements Comparable<Item> {

    @Id
    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Long price;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

 
    @Override
    public int compareTo(Item o) {
        return (int) (this.id - o.getId());
    }
}
