package com.haiduk.springcourse.util;

import com.haiduk.springcourse.dao.PersonDAO;
import com.haiduk.springcourse.models.Person;
import com.haiduk.springcourse.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Person person = (Person) o;
        if (peopleService.findOneByFullName(person.getFullName()).isPresent()) {
            errors.rejectValue("fullName", "", "This full name is already taken");
        }

    }
}
