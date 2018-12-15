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
public class Message extends IdEntity{
    private Long id;
    private User user;
    private String name;
    private String description;
    private int price;
    private CurrencyType currencyType;
    private List<Category> categories;

    public Message() {
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
    public int getPrice() {
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
    public static Message createFromJson(String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        Message message = null;

        try {
            message = objectMapper.readValue(jsonString, Message.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return message;
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

    public void setPrice(int price) {
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
        Message message = (Message) o;
        return price == message.price &&
                Objects.equals(id, message.id) &&
                Objects.equals(user, message.user) &&
                Objects.equals(name, message.name) &&
                Objects.equals(description, message.description) &&
                currencyType == message.currencyType &&
                Objects.equals(categories, message.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, name, description, price, currencyType, categories);
    }

    @Override
    public String toString() {
        return "Message{" +
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
