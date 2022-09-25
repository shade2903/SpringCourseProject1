package com.haiduk.springcourse.repositories;

import com.haiduk.springcourse.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.peer.LightweightPeer;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {
    List<Book> findAllByOrderByYear();
    List<Book> findByTitleStartingWith(String title);

}
