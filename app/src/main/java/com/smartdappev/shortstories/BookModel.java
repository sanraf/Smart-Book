package com.smartdappev.shortstories;

public class BookModel {
    String author;
    String title;
    String description;
    String imageuri;
    String bookuri;

    public BookModel() {
    }

    public BookModel(String author, String title, String description, String imageuri, String bookuri) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.imageuri = imageuri;
        this.bookuri = bookuri;
    }



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getBookuri() {
        return bookuri;
    }

    public void setBookuri(String bookuri) {
        this.bookuri = bookuri;
    }
}
