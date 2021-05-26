package com.example.myapplication;

public class Problem_Details {
    String username, phonenum, email, problem, vehicleno, lattitude, longitude;

    public Problem_Details(String username, String phonenum, String email, String problem, String vehicleno, String lattitude, String longitude) {
        this.username = username;
        this.phonenum = phonenum;
        this.email = email;
        this.problem = problem;
        this.vehicleno = vehicleno;
        this.lattitude = lattitude;
        this.longitude = longitude;
    }
    public Problem_Details()
    {}


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhonenum() {
        return phonenum;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getVehicleno() {
        return vehicleno;
    }

    public void setVehicleno(String vehicleno) {
        this.vehicleno = vehicleno;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
