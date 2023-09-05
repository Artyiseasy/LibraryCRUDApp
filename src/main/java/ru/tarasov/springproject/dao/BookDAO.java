package ru.tarasov.springproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tarasov.springproject.models.Book;
import ru.tarasov.springproject.models.Person;


import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    // выводит список всех книг
    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    // возвращает список с поиощью стрима, ищется по списку нужный объект и вовращает его или возвращает null
    public Book show(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id =?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Book.class))
                .stream().findAny().orElse(null);
    }
    // сохраняем книгу
    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book(book_name, year_of_production, author_name) VALUES(?,?,?)", book.getBook_name(),
                book.getYear_of_production(), book.getAuthor_name());
    }
    // изменям данные
    public void update(int id, Book updateBook) {
        jdbcTemplate.update("UPDATE Book SET book_name =?,year_of_prodution =?, author_name=?, WHERE id = ?", updateBook.getBook_name(),
                updateBook.getYear_of_production(), updateBook.getAuthor_name(), id);
    }
    //удаляем книгу
    public void delete(int id) {
        jdbcTemplate.update("DELETE from Book where id = ?", id);
    }
    //освобождаем книгу
    public void release(int id){
        jdbcTemplate.update("update Book set Person_id = NULL where id =?", id);
    }
    //присваеваем книгу человеку
    public void assign (int id, Person selectedPerson){
        jdbcTemplate.update("Update book SET person_id =? where id =?", selectedPerson.getId(), id);
    }
    //получаем человека у которого на рука книга по id книги, если такой имеется
    public Optional<Person> getBookOwner(int id){
        return jdbcTemplate.query("Select Person.* from Book Join Person on person.id = book.person_id"
                + " where book.id = ?", new Object[]{id}, new BeanPropertyRowMapper(Person.class))
                .stream().findAny();
    }
}
