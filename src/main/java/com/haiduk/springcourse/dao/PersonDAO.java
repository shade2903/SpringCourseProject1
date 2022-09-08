package com.haiduk.springcourse.dao;

import com.haiduk.springcourse.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

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

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO PERSON (full_name,year_of_birth) VALUES (?,?)",
                person.getFullName(), person.getYearOfBirth());
    }

    public Optional<Person> showByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE full_name =?", new Object[]{fullName},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM PERSON WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class))
                .stream().findAny().orElse(null);
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE Person SET full_name=?, year_of_birth=? WHERE id=?",
                person.getFullName(), person.getYearOfBirth(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);

    }
}
