package com.library.model;

import com.google.gson.annotations.SerializedName;
import com.library.R;

import java.io.Serializable;

public class ImportForm implements Serializable {
    private static final int[] STATE_BACKGROUNDS = {R.drawable.bgs_pending, R.drawable.bgs_processing, R.drawable.bgs_completed, R.drawable.bgs_canceled};
    private static final int[] STATE_IMAGES = {R.drawable.state_sell_01, R.drawable.state_sell_02, R.drawable.state_sell_03, R.drawable.state_sell_04};
    private static final String[] STATE_NAMES = {"CHỜ DUYỆT", "ĐANG XỬ LÝ", "ĐÃ NHẬP", "ĐÃ HỦY"};
    private int id;
    @SerializedName("librarian")
    private int librarianId;
    @SerializedName("created_date")
    private String createdDate;
    private String supplier;
    private float total;
    private int state;

    public ImportForm() {
    }

    public ImportForm(String supplier) {
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStateBackground() {
        return STATE_BACKGROUNDS[state];
    }

    public int getStateImage() {
        return STATE_IMAGES[state];
    }

    public String getStateName() {
        return STATE_NAMES[state];
    }
}
