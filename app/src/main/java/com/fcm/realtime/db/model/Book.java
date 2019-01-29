package com.fcm.realtime.db.model;

public class Book {

    private String bookname;
    private String authore;

    public Book() {
    }

    public Book(String bookname, String authore) {
        this.bookname = bookname;
        this.authore = authore;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthore() {
        return authore;
    }

    public void setAuthore(String authore) {
        this.authore = authore;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookname='" + bookname + '\'' +
                ", authore='" + authore + '\'' +
                '}';
    }
}
