package com.fiveware.model;


import com.google.common.base.Objects;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "input_field")
public class InputField implements Serializable,Comparable<InputField>{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy="inputFields")
    private List<Bot> bots;

    public InputField(){}

    public InputField(String nome) {
        this.name=nome;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    public List<Bot> getBots() {
        return bots;
    }


    public void setBots(List<Bot> bots) {
        this.bots = bots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputField that = (InputField) o;
        return Objects.equal(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, name);
    }

    @Override
    public int compareTo(InputField o) {
        if (o.id == null) return -1;
        return o.id.compareTo(this.id);
    }
}
