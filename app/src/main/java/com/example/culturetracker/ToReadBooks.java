package com.example.culturetracker;

public class ToReadBooks {

    //fields
    private String title;
    private String author;

    //constructor
    public ToReadBooks(String title, String author){
        this.title = title;
        this.author = author;
    }

    //methods
    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }
}
