package com.library.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SellFormDetail implements Serializable {
    private int id;
    @SerializedName("sell_form")
    private int sellFormId;
    @SerializedName("book")
    private int bookId;
    private int quantity;
    private double price;

    public SellFormDetail() {
    }

    public SellFormDetail(int bookId, int quantity, double price) {
        this.bookId = bookId;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSellFormId() {
        return sellFormId;
    }

    public void setSellFormId(int sellFormId) {
        this.sellFormId = sellFormId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
