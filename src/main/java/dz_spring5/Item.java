package dz_spring5;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.Objects;

@Entity
@Table(name = "ITEM_SPRING")
public class Item {
    private Long id;
    private String description;

    @Id
    @SequenceGenerator(name = "IT_SQ", sequenceName = "ITEM_SPRING_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IT_SQ")
    public Long getId() {
        return id;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    @JsonCreator
    public static Item createFromJson(String jsonString){

        ObjectMapper objectMapper = new ObjectMapper();

        Item item = null;
        try {
            item = objectMapper.readValue(jsonString, Item.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
