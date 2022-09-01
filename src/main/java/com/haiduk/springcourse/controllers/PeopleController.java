package com.haiduk.springcourse.controllers;

import com.haiduk.springcourse.dao.PersonDAO;
import com.haiduk.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;

@Autowired
    public PeopleController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }
    @GetMapping()
    public String index(Model model){
    model.addAttribute("people", personDAO.index());
    return "people/index";

    }
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person){
    return "people/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("person") Person person){
        System.out.println(person.getFullName());
        System.out.println(person.getYearOfBirth());
    personDAO.save(person);
    return "redirect:/people";
    }
}
