package com.haiduk.springcourse.models;

public class Book {
    private int id;
    private String title;
    private String author;
    private int age;

    public Book() {
    }

    public Book(int id, String title, String author, int age) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
