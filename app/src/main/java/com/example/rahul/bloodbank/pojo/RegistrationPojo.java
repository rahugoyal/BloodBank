package com.example.rahul.bloodbank.pojo;

/**
 * Created by Rahul on 11/20/2016.
 */

public class RegistrationPojo {
    private String phone;
    private String address;
    private String email;
    private String bgType;
    private String city;
    private String name;
    private String userType;
    private String gender;
    private String photo;
    private String username;
    private String password;

    public RegistrationPojo() {
    }

    public RegistrationPojo(String gender, String address, String email, String bgType, String city, String name, String userType, String password, String username, String photo, String phone) {
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.bgType = bgType;
        this.city = city;
        this.name = name;
        this.userType = userType;
        this.password = password;
        this.username = username;
        this.photo = photo;
        this.phone = phone;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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
}
