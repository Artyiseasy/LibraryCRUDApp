package ru.tarasov.springproject.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tarasov.springproject.dao.BookDAO;
import ru.tarasov.springproject.models.Book;
import ru.tarasov.springproject.models.Person;
import ru.tarasov.springproject.services.BookServices;
import ru.tarasov.springproject.services.PersonServices;



@Controller
@RequestMapping("/books")
public class BookController {

   private BookDAO bookDAO;
   private BookServices bookServices;
   private PersonServices personServices;

   @Autowired
    public BookController(BookServices bookServices, PersonServices personServices) {
        this.bookServices = bookServices;
        this.personServices = personServices;
    }

    @GetMapping()
    public String index(Model model, @RequestParam(value = "page", required = false)Integer page,
                        @RequestParam(value = "book_per_page", required = false)Integer booksPerPage,
                        @RequestParam(value = "sortByYear", required = false)boolean sortByYear){
       if(page == null || booksPerPage == null) {
           model.addAttribute("books", bookServices.findAll(sortByYear));
       }
       else { model.addAttribute("books", bookServices.findWithPagination(page, booksPerPage, sortByYear));}
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model,
    @ModelAttribute("person") Person person ){
        model.addAttribute("book", bookServices.findOne(id));

        Person bookOwner = bookServices.getBookOwner(id);

        //если книга на руках, то выводим имя, если свободна, то выводим список людей
        if (bookOwner != null)
            model.addAttribute("owner", bookOwner);
        else
            model.addAttribute("people", personServices.findAll());
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
        bookServices.save(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}/edit") // метод для изменения данных
    public String edit(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookServices.findOne(id));
        return "books/edit";
    }
    @PostMapping("/{id}")
    public String update(@ModelAttribute("book") @Valid Book book,
                         @PathVariable("id") int id){
        bookServices.update(id, book);
        return "redirect:/books";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookServices.delete(id);
        return "redirect:/books";
    }
    @PostMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookServices.release(id);
        return "redirect:/books/" +id;
    }
    @PostMapping("/{id}/assign")
    public String assign (@PathVariable("id") int id, @ModelAttribute("person") Person selectedPerson){
        bookServices.assign(id, selectedPerson);
        return "redirect:/books/" +id;
    }
    @GetMapping("/search")
    public String searchPage(){
       return "books/search";
    }
     
    @PostMapping("/search")
    public String makeSearch(Model model, @RequestParam("query") String query){
       model.addAttribute("books", bookServices.findByTitle(query));
        return "books/search";
    }
}
