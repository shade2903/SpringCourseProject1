package com.haiduk.springcourse.util;

import com.haiduk.springcourse.dao.PersonDAO;
import com.haiduk.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonDAO personDAO;

@Autowired
    public PersonValidator(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    Person person = (Person) o;
    if(personDAO.showByFullName(person.getFullName()).isPresent()){
        errors.rejectValue("fullName","","This full name is already taken");
    }

    }
}
