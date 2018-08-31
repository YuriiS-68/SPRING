package dz_spring2_2;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.persistence.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ITEM")
public class Item {

    private Long id;
    private String name;
    private Date dateCreated;
    private Date lastUpdateDate;
    private String description;

    public Item() {
    }

    @Id
    @SequenceGenerator(name = "IT_SQ", sequenceName = "ITEM_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IT_SQ")
    Long getId() {
        return id;
    }
    
    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "DATE_CREATED")
    public Date getDateCreated() {
        return dateCreated;
    }

    @JsonFormat(pattern = "dd.MM.yyyy")
    @Column(name = "LAST_UPDATE_DATE")
    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @JsonCreator
    public static Item createFromJson(String jsonString){
        
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        
        objectMapper.setDateFormat(format);
        
        Item item = null;
        try {
            item = objectMapper.readValue(jsonString, Item.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        
        return item;
    }

    void validationObject(Item item) throws BadRequestException {

        Class cls = item.getClass();

        Field[] fields = cls.getDeclaredFields();

        checkFields(fields, item);
    }

    void validationObjectForSave(Item item) throws BadRequestException {

        Class cls = item.getClass();

        Field[] fields = cls.getDeclaredFields();

        fields[0] = null;

        checkFields(fields, item);
    }

    private void checkFields(Field[] fields, Item item)throws BadRequestException{

        for (Field field : fields) {
            try {
                if (field != null && field.get(item) == null) {
                    throw new BadRequestException("Check the entered data. One of the object fields is missing.");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name) &&
                Objects.equals(dateCreated, item.dateCreated) &&
                Objects.equals(lastUpdateDate, item.lastUpdateDate) &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateCreated, lastUpdateDate, description);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateCreated=" + dateCreated +
                ", lastUpdateDate=" + lastUpdateDate +
                ", description='" + description + '\'' +
                '}';
    }
}
