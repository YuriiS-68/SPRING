package dz_spring7.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "CATEGORY_MESSAGE")
public class Category extends IdEntity{
    private Long id;
    private Message message;
    private String name;
    private List<Subcategory> subcategories;

    public Category() {
    }

    @Id
    @SequenceGenerator(name = "CAT_SQ", sequenceName = "CATEGORY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CAT_SQ")
    @Column(name = "ID_CATEGORY")
    @Override
    public Long getId() {
        return id;
    }

    //@OneToMany(mappedBy = "category", fetch = FetchType.LAZY, targetEntity = Message.class)
    @ManyToOne(fetch = FetchType.LAZY)
    //@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MESSAGE_CATEGORY")
    public Message getMessage() {
        return message;
    }

    @Column(name = "CATEGORY_NAME", nullable = false)
    public String getName() {
        return name;
    }

    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, targetEntity = Subcategory.class)
    public List<Subcategory> getSubcategories() {
        return subcategories;
    }

    @JsonCreator
    public static Category createFromJson(String jsonString){

        ObjectMapper objectMapper = new ObjectMapper();

        Category category = null;
        try {
            category = objectMapper.readValue(jsonString, Category.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return category;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(message, category.message) &&
                Objects.equals(name, category.name) &&
                Objects.equals(subcategories, category.subcategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, name, subcategories);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", message=" + message +
                ", name='" + name + '\'' +
                ", subcategories=" + subcategories +
                '}';
    }
}
