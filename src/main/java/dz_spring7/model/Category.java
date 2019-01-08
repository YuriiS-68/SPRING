package dz_spring7.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(name = "CATEGORY_AD")
public class Category extends IdEntity{
    private Long id;
    private Ad ad;
    private String name;
    private SubcategoryType subcategoryType;
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_AD_CATEGORY")
    public Ad getAd() {
        return ad;
    }

    @Column(name = "CATEGORY_NAME", nullable = false)
    public String getName() {
        return name;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "SUBCATEGORY_TYPE", nullable = false)
    public SubcategoryType getSubcategoryType() {
        return subcategoryType;
    }

    @JsonIgnore
    @OneToOne(mappedBy = "category", fetch = FetchType.LAZY, targetEntity = Subcategory.class)
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

    public void setAd(Ad ad) {
        this.ad = ad;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSubcategoryType(SubcategoryType subcategoryType) {
        this.subcategoryType = subcategoryType;
    }

    public void setSubcategories(List<Subcategory> subcategories) {
        this.subcategories = subcategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return id.equals(category.id) &&
                ad.equals(category.ad) &&
                name.equals(category.name) &&
                subcategoryType == category.subcategoryType &&
                subcategories.equals(category.subcategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ad, name, subcategoryType, subcategories);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Category.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("ad=" + ad)
                .add("name='" + name + "'")
                .add("subcategoryType=" + subcategoryType)
                .add("subcategories=" + subcategories)
                .toString();
    }
}
