package com.hd.wing.entity;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "Euser", schema = "")
public class EUser {

    private String id;
    private String name;
    private Integer age;

    public EUser() {
    }

    public EUser(String id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public EUser(String name, Integer age) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.age = age;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "EUser{" + "id=" + id + ", name=" + name + ", age=" + age + '}';
    }

}
