package com.haiduk.springcourse.dao;

import com.haiduk.springcourse.models.Book;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM Book", new BeanPropertyRowMapper<>(Book.class));
    }

    public void save(Book book){
        jdbcTemplate.update("INSERT INTO Book (title, author, year) VALUES (?,?,?)",
                book.getTitle(), book.getAuthor(), book.getYear());
    }

    public Book showById(int id){
        return jdbcTemplate.query("SELECT * FROM Book WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public Book showByPersonId(int personId){
        return jdbcTemplate.query("SELECT * FROM Book WHERE person_id = ?", new Object[]{personId},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void update(int id, Book book){
        jdbcTemplate.update("UPDATE Book SET title = ?, author = ?, year = ?",
                book.getTitle(), book.getAuthor(), book.getYear());
            }

            public void delete(int id){
        jdbcTemplate.update("DELETE FROM Book WHERE id = ?", id);
            }
}
