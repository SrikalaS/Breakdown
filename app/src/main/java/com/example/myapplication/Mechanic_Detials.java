package com.example.myapplication;

public class Mechanic_Detials {
    String username,password,location,phonenum,vehicleno,imageurl,email;
    public Mechanic_Detials()
    {}

    public Mechanic_Detials(String username, String password, String location, String phonenum, String vehicleno, String imageurl, String email) {
        this.username = username;
        this.password = password;
        this.location = location;
        this.phonenum = phonenum;
        this.vehicleno = vehicleno;
        this.imageurl = imageurl;
        this.email = email;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
