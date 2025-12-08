package pn.market.entities;

//import jakarta.persistence.*;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@ToString
@Table(name = "items" , schema = "market")
public class Item {

    @Id
    private Long id;
    private String title;
    private String description;
    private String imgPath;
    private Long price;
    private Integer count;

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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public void plusCount() {
        count++;
    }

    public void minusCount() {
        if (count > 0) count--;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Item{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", imgPath='").append(imgPath).append('\'');
        sb.append(", price=").append(price);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
