package com.library.model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String image;
    private String title;
    private String author;
    private String category;
    private int bookshelf;
    private int shelf;
    private int inventory;
    private int quantity;

    public Book() {
    }

    public Book(int id, String image, String title, String author, String category, int bookshelf, int shelf, int inventory, int quantity) {
        this.id = id;
        this.image = image;
        this.title = title;
        this.author = author;
        this.category = category;
        this.bookshelf = bookshelf;
        this.shelf = shelf;
        this.inventory = inventory;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getBookshelf() {
        return bookshelf;
    }

    public void setBookshelf(int bookshelf) {
        this.bookshelf = bookshelf;
    }

    public int getShelf() {
        return shelf;
    }

    public void setShelf(int shelf) {
        this.shelf = shelf;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}