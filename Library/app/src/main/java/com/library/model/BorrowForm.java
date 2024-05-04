package com.library.model;

import com.google.gson.annotations.SerializedName;
import com.library.R;
import com.library.utilities.DateFormatter;

import java.io.Serializable;
import java.util.Calendar;

public class BorrowForm implements Serializable {
    private static final int[] STATE_IMAGES = {R.drawable.state_borrow_01, R.drawable.state_borrow_01,
            R.drawable.state_borrow_02, R.drawable.state_borrow_03};
    private static final int[] STATE_BACKGROUNDS = {R.drawable.bgs_processing, R.drawable.bgs_completed, R.drawable.bgs_canceled, R.drawable.bgs_canceled};
    private static final String[] STATE_NAMES = {"CÒN HẠN", "ĐÃ TRẢ", "ĐÃ QUÁ HẠN", "ĐÃ HỦY"};
    private int id;
    @SerializedName("reader")
    private int readerId;
    @SerializedName("librarian")
    private int librarianId;
    @SerializedName("created_date")
    private String createdDate;
    @SerializedName("expired_date")
    private String expiredDate;
    private int state;

    public BorrowForm() {
    }

    public BorrowForm(int readerId) {
        this.readerId = readerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getReaderId() {
        return readerId;
    }

    public void setReaderId(int readerId) {
        this.readerId = readerId;
    }

    public int getLibrarianId() {
        return librarianId;
    }

    public void setLibrarianId(int librarianId) {
        this.librarianId = librarianId;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public int getState() {
        boolean is_expired = DateFormatter.parse(expiredDate).before(Calendar.getInstance().getTime());
        return (state == 0 && is_expired) ? 2 : state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStateImage() {
        return STATE_IMAGES[getState()];
    }

    public int getStateBackground() {
        return STATE_BACKGROUNDS[getState()];
    }

    public String getStateName() {
        return STATE_NAMES[getState()];
    }
}
