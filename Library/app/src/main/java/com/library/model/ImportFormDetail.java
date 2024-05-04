package com.library.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ImportFormDetail implements Serializable {
    private int id;
    @SerializedName("import_form")
    private int importFormId;
    @SerializedName("book")
    private int bookId;
    private int quantity;
    private double price;

    public ImportFormDetail() {
    }

    public ImportFormDetail(int bookId, int quantity, double price) {
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

    public int getImportFormId() {
        return importFormId;
    }

    public void setImportFormId(int importFormId) {
        this.importFormId = importFormId;
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
