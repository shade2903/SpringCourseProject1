package com.haiduk.springcourse.dao;

import com.haiduk.springcourse.models.Book;
import com.haiduk.springcourse.models.Person;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES (?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book showById(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public List<Book> showByPersonId(int personId) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{personId}, new BeanPropertyRowMapper<>(Book.class));
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void assignBook(int id, Person personOwner) {
        jdbcTemplate.update("UPDATE Book SET person_id = ? WHERE id = ?",
                personOwner.getId(), id);
    }
    public Optional<Person> getOwnerBook(int idBook){
        return jdbcTemplate.query("SELECT Person.* FROM Book JOIN Person On Book.person_id = Person.id WHERE Book.id = ?",
                new Object[]{idBook}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }

    public void releaseBook(int id) {
        jdbcTemplate.update("UPDATE Book SET person_id = null WHERE id =?", id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
    }
}
