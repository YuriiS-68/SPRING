package dz_spring7.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.persistence.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "USER_DZ7")
public class User extends IdEntity {
    private Long id;
    private String name;
    private String password;
    private List<Message> messages;

    public User() {
    }

    @Id
    @SequenceGenerator(name = "US7_SQ", sequenceName = "USER7_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "US7_SQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    @Column(name = "USER_NAME", nullable = false)
    public String getName() {
        return name;
    }

    @Column(name = "USER_PASSWORD", nullable = false)
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @JsonProperty(value = "messages")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, targetEntity = Message.class)
    public List<Message> getMessages() {
        return messages;
    }

    @JsonCreator
    public static User createFromJson(String jsonString){
        ObjectMapper objectMapper = new ObjectMapper();
        User user = null;

        try {
            user = objectMapper.readValue(jsonString, User.class);
        }catch (IOException e){
            e.printStackTrace();
        }
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(password, user.password) &&
                Objects.equals(messages, user.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, password, messages);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", messages=" + messages +
                '}';
    }
}
