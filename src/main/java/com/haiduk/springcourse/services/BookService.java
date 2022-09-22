package com.haiduk.springcourse.services;

import com.haiduk.springcourse.models.Book;
import com.haiduk.springcourse.models.Person;
import com.haiduk.springcourse.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        Optional<Book> foundPerson = bookRepository.findById(id);
        return foundPerson.orElse(null);
    }

    @Transactional
    public void save(Book book){
        bookRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        bookRepository.save(updatedBook);
    }

    @Transactional
    public void deleteById(int id){
        bookRepository.deleteById(id);
    }

    public List<Book> showByPersonId(int personId){
        return peopleService.findOneById(personId).getBooks();
    }

    @Transactional
    public void assignBook(int id, Person personOwner){
        Book book = bookRepository.getById(id);
        if(book!= null){
            book.setOwner(personOwner);
            bookRepository.save(book);
        }
    }

    public void releaseBook(int id){
        Book book = bookRepository.getById(id);
        if(book!= null){
            book.setOwner(null);
            bookRepository.save(book);
        }
    }

    public Optional<Person> getOwnerBook(int id){
        return Optional.of(bookRepository.getById(id).getOwner());
    }

}
