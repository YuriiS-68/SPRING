package dz_spring7.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "MESSAGE")
public class Ad extends IdEntity{
    private Long id;
    private User user;
    private String name;
    private String description;
    private Integer price;
    private CurrencyType currencyType;
    private List<Category> categories;

    public Ad() {
    }

    @Id
    @SequenceGenerator(name = "MSG_SQ", sequenceName = "MESSAGE_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MSG_SQ")
    @Column(name = "ID_MESSAGE")
    @Override
    public Long getId() {
        return id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_USER")
    public User getUser() {
        return user;
    }

    @Column(name = "MESSAGE_NAME", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "MESSAGE_DESCRIPTION", nullable = false)
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

    @JsonIgnore
    @OneToMany(mappedBy = "message", fetch = FetchType.LAZY, targetEntity = Category.class)
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

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return price.equals(ad.price) &&
                Objects.equals(id, ad.id) &&
                Objects.equals(user, ad.user) &&
                Objects.equals(name, ad.name) &&
                Objects.equals(description, ad.description) &&
                currencyType == ad.currencyType &&
                Objects.equals(categories, ad.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, name, description, price, currencyType, categories);
    }

    @Override
    public String toString() {
        return "Ad{" +
                "id=" + id +
                ", user=" + user +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", currencyType=" + currencyType +
                ", categories=" + categories +
                '}';
    }
}
