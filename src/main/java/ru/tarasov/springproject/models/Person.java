package ru.tarasov.springproject.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {

    @Id()
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "N ame should be between 2 and 100 characters")
    @Column(name = "name")
    private String name;

    @Min(value = 0, message = "age should be more than 0")
    @Max(value = 115, message = "age should be less than 115")
    @Column(name = "age")
    private int age;

    @OneToMany(mappedBy = "owner")
    private List<Book> bookList;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Person() {
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public int getAge() {return age;}
    public void setAge(int age) {this.age = age;}

    public List<Book> getBookList() {return bookList;}
    public void setBookList(List<Book> bookList) {this.bookList = bookList;}
    }
