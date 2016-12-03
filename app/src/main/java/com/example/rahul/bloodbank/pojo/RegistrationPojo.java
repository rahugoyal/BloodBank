package com.example.rahul.bloodbank.pojo;


import java.io.Serializable;

/**
 * Created by Rahul on 11/20/2016.
 */

public class RegistrationPojo implements Serializable {
    private String phone;
    private String address;
    private String email;
    private String bgType;
    private String city;
    private String name;
    private String userType;
    private String gender;
    private String username;
    private String password;
    private boolean donorStatus;
    private boolean acceptorStatus;

    public RegistrationPojo() {
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBgType() {
        return bgType;
    }

    public void setBgType(String bgType) {
        this.bgType = bgType;
    }

    public boolean isDonorStatus() {
        return donorStatus;
    }

    public void setDonorStatus(boolean donorStatus) {
        this.donorStatus = donorStatus;
    }

    public boolean isAcceptorStatus() {
        return acceptorStatus;
    }

    public void setAcceptorStatus(boolean acceptorStatus) {
        this.acceptorStatus = acceptorStatus;
    }
}
