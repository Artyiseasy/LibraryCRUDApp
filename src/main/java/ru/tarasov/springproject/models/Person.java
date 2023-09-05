package ru.tarasov.springproject.models;


import jakarta.validation.constraints.*;


public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size (min = 2, max = 100, message = "name should be between 2 and 100 characters")
    private String name;

    @Min(value = 1900, message = "year should be greater than 1900")
    @Max(value = 2023, message = "year should be less than 2023")
    private int year_of_birthday;

    public Person(int id, String name, int yearOfBirthday) {
        this.id = id;
        this.name = name;
        this.year_of_birthday = yearOfBirthday;
    }

    public Person(){}


    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id;}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getYear_of_birthday() {return year_of_birthday;}
    public void setYear_of_birthday(int year_of_birthday) {this.year_of_birthday = year_of_birthday;}

}
