package dz_spring7.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
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
    private String city;
    private String numberPhone;
    private Date dateFrom;
    private Date dateTo;
    private CurrencyType currencyType;
    private CategoryType categoryType;
    private SubcategoryType subcategoryType;

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

    @Column(name = "CITY", nullable = false)
    public String getCity() {
        return city;
    }

    @Column(name = "NUMBER_PHONE", nullable = false)
    public String getNumberPhone() {
        return numberPhone;
    }

    @Column(name = "DATE_FROM", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'hh:mm")
    public Date getDateFrom() {
        return dateFrom;
    }

    @Column(name = "DATE_TO", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'hh:mm")
    public Date getDateTo() {
        return dateTo;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "SUBCATEGORY_TYPE", nullable = false)
    public SubcategoryType getSubcategoryType() {
        return subcategoryType;
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

    public void setCity(String city) {
        this.city = city;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public void setCurrencyType(CurrencyType currencyType) {
        this.currencyType = currencyType;
    }

    public void setCategoryType(CategoryType categoryType) {
        this.categoryType = categoryType;
    }

    public void setSubcategoryType(SubcategoryType subcategoryType) {
        this.subcategoryType = subcategoryType;
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
                city.equals(ad.city) &&
                numberPhone.equals(ad.numberPhone) &&
                dateFrom.equals(ad.dateFrom) &&
                dateTo.equals(ad.dateTo) &&
                currencyType == ad.currencyType &&
                categoryType == ad.categoryType &&
                subcategoryType == ad.subcategoryType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, name, description, price, city, numberPhone, dateFrom, dateTo, currencyType, categoryType, subcategoryType);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Ad.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("user=" + user.getId())
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("price=" + price)
                .add("city='" + city + "'")
                .add("numberPhone='" + numberPhone + "'")
                .add("dateFrom=" + dateFrom)
                .add("dateTo=" + dateTo)
                .add("currencyType=" + currencyType)
                .add("categoryType=" + categoryType)
                .add("subcategoryType=" + subcategoryType)
                .toString();
    }
}
