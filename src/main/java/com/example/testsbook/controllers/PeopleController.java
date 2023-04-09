package com.example.testsbook.controllers;

import com.example.testsbook.models.Person;
import com.example.testsbook.service.BookService;
import com.example.testsbook.service.PeopleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final BookService bookService;
    @Autowired
    public PeopleController(PeopleService peopleService, BookService bookService) {
        this.peopleService = peopleService;
        this.bookService = bookService;
    }

    @GetMapping()
    public String findPeople(Model model){
        model.addAttribute("people",peopleService.findAll());
        model.addAttribute("role",peopleService.findRoleByName());
        return "people/indexPerson";
    }
    @GetMapping("/{id}")
    public String findPerson(@PathVariable("id") Long id, Model model){
        model.addAttribute("person",peopleService.findPerson(id));
        model.addAttribute("user_books",bookService.findUserBooks(id));

        return "people/showPerson";
    }

    @GetMapping("/{id}/edit")
    public String editPerson(Model model, @PathVariable("id") Long id){
        model.addAttribute("person",peopleService.findPerson(id));
        return "people/editPerson";
    }

    @PostMapping("/{id}")
    public String updatePerson(@ModelAttribute("person") Person person,
                               BindingResult bindingResult, @PathVariable("id") Long id){
        if(bindingResult.hasErrors()){
            return "people/editPerson";
        }
        peopleService.updatePerson(id,person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") Long id){
        peopleService.delete(id);
        return "redirect:/people";
    }

}