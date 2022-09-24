package com.haiduk.springcourse.services;

import com.haiduk.springcourse.models.Book;
import com.haiduk.springcourse.models.Person;
import com.haiduk.springcourse.repositories.BookRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BookRepository bookRepository;
    private final PeopleService peopleService;

    @Autowired
    public BookService(BookRepository bookRepository, PeopleService peopleService) {
        this.bookRepository = bookRepository;
        this.peopleService = peopleService;
    }
    public List<Book> index(Boolean sortByYear){
        if(sortByYear){
            return bookRepository.findAllByOrderByYear();
        }else {
            return bookRepository.findAll();
        }
    }

    public List<Book> paginationPage(int page, int booksPerPage, boolean sorted){
        if(sorted){
            return bookRepository.findAll(PageRequest.of(page,booksPerPage,Sort.by("year"))).getContent();
        }
        return bookRepository.findAll(PageRequest.of(page,booksPerPage)).getContent();
    }

    public List<Book> findByTitle(String title){
        return bookRepository.findByTitleStartingWith(title);
    }

    public Book showById(int id){
        Optional<Book> foundBook = bookRepository.findById(id);
        return foundBook.orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        Book bookToBeUpdated = bookRepository.findById(id).get();
        updatedBook.setId(id);
        updatedBook.setOwner(bookToBeUpdated.getOwner());
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void deleteById(int id){
        bookRepository.deleteById(id);
    }

    public List<Book> showByPersonId(int personId){
        Optional<Person> person = Optional.of(peopleService.findOneById(personId));
        if (person.isPresent()){
            Hibernate.initialize(person.get().getBooks());
            return person.get().getBooks();
        } else {
            return Collections.emptyList();
        }


    }

    @Transactional
    public void assignBook(int id, Person personOwner){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            book.get().setOwner(personOwner);
        }
    }


    @Transactional
    public void releaseBook(int id){
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            book.get().setOwner(null);
        }
    }

    public Person getOwnerBook(int id){
        return bookRepository.findById(id).get().getOwner();
    }

}
