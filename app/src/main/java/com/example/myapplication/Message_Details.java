package com.example.myapplication;

public class Message_Details {
    String username,problem,mechanicname;

    public Message_Details(String username, String problem, String mechanicname) {
        this.username = username;
        this.problem = problem;
        this.mechanicname = mechanicname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getMechanicname() {
        return mechanicname;
    }

    public void setMechanicname(String mechanicname) {
        this.mechanicname = mechanicname;
    }
}
