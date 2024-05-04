package com.library.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BorrowFormDetail implements Serializable {
    private static final String[] STATE_NAMES = {"ĐANG MƯỢN", "ĐÃ TRẢ", "ĐÃ MẤT"};
    private int id;
    @SerializedName("borrow_form")
    private int borrowFormId;
    @SerializedName("book")
    private int bookId;
    private int quantity;
    @SerializedName("returned_date")
    private String returnedDate;
    @SerializedName("returned_quantity")
    private int returnedQuantity;
    private int state;

    public BorrowFormDetail() {
    }

    public BorrowFormDetail(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public static String[] getStateNames() {
        return STATE_NAMES;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBorrowFormId() {
        return borrowFormId;
    }

    public void setBorrowFormId(int borrowFormId) {
        this.borrowFormId = borrowFormId;
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

    public String getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(String returnedDate) {
        this.returnedDate = returnedDate;
    }

    public int getReturnedQuantity() {
        return returnedQuantity;
    }

    public void setReturnedQuantity(int returnedQuantity) {
        this.returnedQuantity = returnedQuantity;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
