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
@Table(name = "AD")
public class Ad extends IdEntity{
    private Long id;
    private User user;
    private String name;
    private String description;
    private Integer price;
    private CurrencyType currencyType;
    private CategoryType categoryType;
    private List<Category> categories;

    public Ad() {
    }

    @Id
    @SequenceGenerator(name = "AD_SQ", sequenceName = "AD_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AD_SQ")
    @Column(name = "ID_AD")
    @Override
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER")
    public User getUser() {
        return user;
    }

    @Column(name = "AD_NAME", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "AD_DESCRIPTION", nullable = false)
    public String getDescription() {
        return description;
    }

    @Column(name = "PRICE", nullable = false)
    public Integer getPrice() {
        return price;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "CURRENCY_TYPE", nullable = false)
    public CurrencyType getCurrencyType() {
        return currencyType;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "CATEGORY_TYPE", nullable = false)
    public CategoryType getCategoryType() {
        return categoryType;
    }

    @JsonIgnore
    @OneToOne(mappedBy = "ad", fetch = FetchType.LAZY, targetEntity = Category.class)
    public List<Category> getCategories() {
        return categories;
    }

    @JsonCreator
    public static Ad createFromJson(String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        Ad ad = null;

        try {
            ad = objectMapper.readValue(jsonString, Ad.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return ad;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return id.equals(ad.id) &&
                user.equals(ad.user) &&
                name.equals(ad.name) &&
                description.equals(ad.description) &&
                price.equals(ad.price) &&
                currencyType == ad.currencyType &&
                categoryType == ad.categoryType &&
                categories.equals(ad.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, name, description, price, currencyType, categoryType, categories);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ad.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("price=" + price)
                .add("currencyType=" + currencyType)
                .add("categoryType=" + categoryType)
                .add("categories=" + categories)
                .toString();
    }
}
