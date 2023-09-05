package ru.tarasov.springproject.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tarasov.springproject.dao.BookDAO;
import ru.tarasov.springproject.dao.PersonDAO;
import ru.tarasov.springproject.models.Book;
import ru.tarasov.springproject.models.Person;

import java.util.Optional;


@Controller
@RequestMapping("/books")
public class BookController {

   private BookDAO bookDAO;
   private PersonDAO personDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
    @ModelAttribute("person") Person person ){
        model.addAttribute("book", bookDAO.show(id));

        Optional<Person> bookOwner = bookDAO.getBookOwner(id);

        //если книга на руках, то выводим имя, если свободна, то выводим список людей
        if (bookOwner.isPresent())
            model.addAttribute("owner", bookOwner.get());
        else
            model.addAttribute("people", personDAO.index());
        return "books/show";
    }

    @GetMapping("/new")
    public String newBook(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping() // метод для добавления книги в базу данных
    // BindingResult используется для ошибок. надо писать сразу после аргумета, у которого есть тег @Valid
    public String create(@ModelAttribute("book") @Valid Book book) {
        bookDAO.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit") // метод для изменения данных
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }
    @PostMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("id") int id){
        bookDAO.update(id, book);
        return "redirect/book";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.release(id);
        return "redirect:/books/" +id;
    }
    @PostMapping("/{id}/assign")
    public String assign (@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookDAO.assign(id,selectedPerson);
        return "redirect:/books/" +id;
    }

}
