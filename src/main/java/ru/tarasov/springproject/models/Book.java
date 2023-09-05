package ru.tarasov.springproject.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String book_name;
    @NotEmpty
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String author_name;
    @Min(value = 1465, message = "year should be more 1465")
    private int year_of_production;

    public Book(int id,String book_name, String author_name, int year_of_production) {
        this.book_name = book_name;
        this.author_name = author_name;
        this.year_of_production = year_of_production;
        this.id = id;
    }
    public Book(){}

    public String getBook_name() {return book_name;}
    public void setBook_name(String book_name) {this.book_name = book_name;}

    public String getAuthor_name() {return author_name;}
    public void setAuthor_name(String author_name) {this.author_name = author_name;}

    public int getYear_of_production() {return year_of_production;}
    public void setYear_of_production(int year_of_production) {this.year_of_production = year_of_production;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}
