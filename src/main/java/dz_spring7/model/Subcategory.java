package dz_spring7.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "SUBCATEGORY")
public class Subcategory extends IdEntity{
    private Long id;
    private Category category;
    private String name;
    private String city;
    private String numberPhone;
    private Date dateFrom;
    private Date dateTo;

    public Subcategory() {
    }

    @Id
    @SequenceGenerator(name = "SUB_SQ", sequenceName = "SUBCATEGORY_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUB_SQ")
    @Column(name = "ID_SUBCATEGORY")
    @Override
    public Long getId() {
        return id;
    }

    //@OneToMany(mappedBy = "subcategory", fetch = FetchType.LAZY, targetEntity = Category.class)
    //@ManyToOne(fetch = FetchType.LAZY)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_CATEGORY_SUBCATEGORY")
    public Category getCategory() {
        return category;
    }

    @Column(name = "SUBCATEGORY_NAME", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "SUBCATEGORY_CITY", nullable = false)
    public String getCity() {
        return city;
    }

    @Column(name = "NUMBER_PHONE", nullable = false)
    public String getNumberPhone() {
        return numberPhone;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_FROM", nullable = false)
    public Date getDateFrom() {
        return dateFrom;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "DATE_TO", nullable = false)
    public Date getDateTo() {
        return dateTo;
    }

    @JsonCreator
    public static Subcategory createFromJson(String jsonString){

        ObjectMapper objectMapper = new ObjectMapper();

        Subcategory subcategory = null;
        try {
            subcategory = objectMapper.readValue(jsonString, Subcategory.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return subcategory;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subcategory that = (Subcategory) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(category, that.category) &&
                Objects.equals(name, that.name) &&
                Objects.equals(city, that.city) &&
                Objects.equals(numberPhone, that.numberPhone) &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, category, name, city, numberPhone, dateFrom, dateTo);
    }

    @Override
    public String toString() {
        return "Subcategory{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                '}';
    }
}
