package ru.tarasov.springproject.services;


import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tarasov.springproject.models.Book;
import ru.tarasov.springproject.models.Person;
import ru.tarasov.springproject.repositories.PersonRepositories;

import javax.xml.crypto.Data;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PersonServices {
    private PersonRepositories personRepositories;
    @Autowired
    public PersonServices(PersonRepositories personRepositories) {this.personRepositories = personRepositories; }

    public List<Person> findAll(){
       return personRepositories.findAll();
    }
    public Person findOne(int id){
        Optional<Person> person = personRepositories.findById(id);
        return person.orElse(null);
    }
    @Transactional
    public void save(Person person){
        personRepositories.save(person);
    }

    @Transactional
    public void update(int id, Person updatePerson){
        updatePerson.setId(id);
        personRepositories.save(updatePerson);
    }
    @Transactional
    public void deleteById(int id){
        personRepositories.deleteById(id);
    }

    public Optional<Person> getPersonByName(String name){ return personRepositories.findByName(name);}

    public List<Book> getBookByPersonId(int id){
        Optional<Person> person = personRepositories.findById(id);
        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBookList());

            person.get().getBookList().forEach(book -> {
                long diffInMiles = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (diffInMiles > 864000000)
                    book.setExpired(true);
            });
            return person.get().getBookList();
        }
        else {
            return Collections.emptyList();
        }
    }
    }
