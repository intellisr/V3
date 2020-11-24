package com.mint.dataModel;

import javax.persistence.*;

@Entity
@Table(name = "user") //This model will be creating/using table 'user'
public class User {
    @Id //Primary key of this talbe is column 'Id'
    @GeneratedValue(strategy = GenerationType.AUTO) // Primary key is auto generated
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="full_name")
    private String fullName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}