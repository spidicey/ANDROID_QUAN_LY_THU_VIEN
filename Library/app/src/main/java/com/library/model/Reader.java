package com.library.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Reader implements Serializable {
    private int id;
    @SerializedName("last_name")
    private String lastName;
    @SerializedName("first_name")
    private String firstName;
    private Boolean gender;
    private String birthdate;
    private String address;
    private String phone;
    private boolean state;
    private String profile_image_path;

    public Reader() {
    }

    public Reader(int id, String lastName, String firstName, boolean gender, String birthdate, String address, String phone, boolean state, String profile_image_path) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.gender = gender;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.state = state;
        this.profile_image_path = profile_image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getProfile_image_path() {
        return profile_image_path;
    }

    public void setProfile_image_path(String profile_image_path) {
        this.profile_image_path = profile_image_path;
    }

    public String getFullName() {
        return String.format("%s %s", lastName, firstName);
    }

    public String getStateName() {
        return state ? "HOẠT ĐỘNG" : "ĐÃ NGHỈ";
    }
}
