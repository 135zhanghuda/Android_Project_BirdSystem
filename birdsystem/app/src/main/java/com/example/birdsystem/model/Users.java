package com.example.birdsystem.model;

public class Users {
    public String username;
    public String password;
    public int power;
    public String personal;

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public String getUsername() {
        return username;
    }

    public int getPower() {
        return power;
    }

    public String getPassword() {
        return password;
    }

    public String getPersonal() {
        return personal;
    }
}
