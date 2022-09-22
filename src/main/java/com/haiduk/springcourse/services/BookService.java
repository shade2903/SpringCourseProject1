package com.haiduk.springcourse.services;

import com.haiduk.springcourse.models.Book;
import com.haiduk.springcourse.models.Person;
import com.haiduk.springcourse.repositories.BookRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Book> index(){
        return bookRepository.findAll();
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
        bookRepository.findById(id).ifPresent(
                book -> {
                    book.setOwner(personOwner);
                }
        );
    }

    public void releaseBook(int id){
     Book book = bookRepository.getById(id);
     Person person = peopleService.findOneById(book.getOwner().getId());
     List<Book> books = person.getBooks();
     books.remove(book);
        System.out.println(person);
        book.setOwner(null);
        System.out.println(book);
     person.setBooks(books);
        System.out.println(book.getOwner());
        System.out.println("books updated for person");

        bookRepository.save(book);
        System.out.println("Save book and person");

    }

    public Person getOwnerBook(int id){
        return bookRepository.findById(id).map(Book::getOwner).orElse(null);
    }

}
