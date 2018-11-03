package com.lesson5;

import javax.persistence.*;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
