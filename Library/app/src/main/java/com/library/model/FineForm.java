package com.library.model;

import com.google.gson.annotations.SerializedName;
import com.library.R;

import java.io.Serializable;

public class FineForm implements Serializable {
    private static final int[] STATE_IMAGES = {R.drawable.state_fine_01, R.drawable.state_fine_02};
    private static final int[] STATE_BACKGROUNDS = {R.drawable.bgs_completed, R.drawable.bgs_canceled};
    private static final String[] STATE_NAMES = {"HOÀN THÀNH", "ĐÃ HỦY"};
    private int id;
    @SerializedName("created_date")
    private String createdDate;
    private float fee;
    private String reason;
    @SerializedName("is_deleted")
    private boolean isDeleted;
    @SerializedName("reader")
    private int readerId;
    @SerializedName("librarian")
    private int librarianId;

    public FineForm() {
    }

    public FineForm(String reason, float fee, int readerId) {
        this.reason = reason;
        this.fee = fee;
        this.readerId = readerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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

    public int getStateImage() {
        return STATE_IMAGES[isDeleted ? 1 : 0];
    }

    public int getStateBackground() {
        return STATE_BACKGROUNDS[isDeleted ? 1 : 0];
    }

    public String getStateName() {
        return STATE_NAMES[isDeleted ? 1 : 0];
    }
}
