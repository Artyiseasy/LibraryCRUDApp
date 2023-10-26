package ru.tarasov.springproject.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.tarasov.springproject.models.Person;
import ru.tarasov.springproject.services.BookServices;
import ru.tarasov.springproject.services.PersonServices;

@Controller
@RequestMapping("/people")
public class PeopleController {



    private PersonServices personServices;


    @Autowired
    public PeopleController(PersonServices personServices) {
        this.personServices = personServices;

    }


    @GetMapping()
    public String index(Model model){
        //получим всех людей из DAO и передадим результат в представление
        model.addAttribute("people", personServices.findAll());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model){
        //получим одного человека из DAO по ID и передадим на отображение в представление
        model.addAttribute("person", personServices.findOne(id));
        model.addAttribute("books", personServices.getBookByPersonId(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String newPerson(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping() // метод для добавления человка в базу данных
    public String create(@ModelAttribute("person") @Valid Person person) {
        personServices.save(person);
        return "redirect:/people";

    }
    @GetMapping("/{id}/edit") // метод для изменения данных
           public String edit(Model model, @PathVariable("id") int id){
            model.addAttribute("person", personServices.findOne(id));
            return "people/edit";
    }
    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person,
           @PathVariable("id") int id){
        personServices.update(id, person);
        return "redirect:/people";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
       personServices.deleteById(id);
        return "redirect:/people";
    }
}

