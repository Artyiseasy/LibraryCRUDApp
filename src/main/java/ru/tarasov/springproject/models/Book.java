package ru.tarasov.springproject.models;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;


import java.util.Date;


@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_name")
    @NotEmpty(message = "Can,t be empty")
    private String title;

    @Column(name = "author")
    @NotEmpty(message = "Can,t be empty")
    private String author;

    @Column(name = "year_of_production")
    @Max(value = 2023, message = "year can't be more then 2023")
    @Min(value = 1650, message = "year can't be less then 1650")
    private int year;

    @ManyToOne()
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private  boolean expired;

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAuthor() {return author;}
    public void setAuthor(String author) {this.author = author;}

    public int getYear() {return year;}
    public void setYear(int year) {this.year = year;}

    public Person getOwner() {return owner;}
    public void setOwner(Person owner) {this.owner = owner;}

    public Date getTakenAt() {return takenAt;}
    public void setTakenAt(Date takenAt) {this.takenAt = takenAt;}

    public boolean isExpired() {return expired;}
    public void setExpired(boolean expired) {this.expired = expired;}
}
