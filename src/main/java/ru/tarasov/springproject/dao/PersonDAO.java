package ru.tarasov.springproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.tarasov.springproject.models.Book;
import ru.tarasov.springproject.models.Person;
import java.util.List;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM Person", new BeanPropertyRowMapper<>(Person.class));
    }

    // возвращает список, но с поиощью стрима, ищется по списку нужный объект и вовращает его или возвращает null
    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM Person WHERE id =?",
                        new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO Person(name, year_of_birthday) VALUES(?,?)", person.getName(),
                person.getYear_of_birthday());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE Person SET name =?,year_of_birthday =?, WHERE id = ?", updatePerson.getName(),
                updatePerson.getYear_of_birthday(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE from Person where id = ?", id);
    }
    public List<Book> getBooksByPersonId(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id =?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class));
    }
}


