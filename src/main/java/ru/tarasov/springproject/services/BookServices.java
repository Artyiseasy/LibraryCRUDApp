package ru.tarasov.springproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.tarasov.springproject.models.Book;
import ru.tarasov.springproject.models.Person;
import ru.tarasov.springproject.repositories.BookRepositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookServices {
    private BookRepositories bookRepositories;

    @Autowired
    public BookServices(BookRepositories bookRepositories) {
        this.bookRepositories = bookRepositories;
    }

    public List<Book> findAll(boolean sortByYear){
        if(sortByYear)
        return bookRepositories.findAll(Sort.by("year"));
        else return bookRepositories.findAll();
    }
    public Book findOne(int id) {
        Optional<Book> book = bookRepositories.findById(id);
        return book.orElse(null);
    }
        public List<Book> findWithPagination (Integer page, Integer booksPerPage, boolean sortByYear) {
            if (sortByYear) {
                return bookRepositories.findAll(PageRequest.of(page, booksPerPage, Sort.by("year"))).getContent();
            } else {
                return bookRepositories.findAll(PageRequest.of(page, booksPerPage)).getContent();
            }
        }

        public List<Book> findByTitle (String query){
            return bookRepositories.findByTitleStartingWith(query);
        }
    @Transactional
    public void save(Book book){
        bookRepositories.save(book);
    }
    @Transactional
    public void update(int id, Book updateBook){
        updateBook.setId(id);
        updateBook.setOwner(updateBook.getOwner());
        bookRepositories.save(updateBook);
    }
    @Transactional
    public void delete(int id){
        bookRepositories.deleteById(id);
    }
    public Person getBookOwner(int id){
        return bookRepositories.findById(id).map(Book :: getOwner).orElse(null);
    }

    @Transactional
    public void release(int id) {
        bookRepositories.findById(id).ifPresent(book ->
        {
            book.setOwner(null);
            book.setTakenAt(null);
        });
    }
        @Transactional
    public void assign(int id, Person selectPerson){
            bookRepositories.findById(id).ifPresent(book ->
            {
                book.setOwner(selectPerson);
                book.setTakenAt(new Date());
            });
        }
}
