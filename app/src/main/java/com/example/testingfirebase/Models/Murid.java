package com.example.testingfirebase.Models;

public class Murid {
    private String email,name,nis,telp;

    public Murid() {

    }
    public Murid(String email, String name, String nis, String telp) {
        this.email = email;
        this.name = name;
        this.nis = nis;
        this.telp = telp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNis() {
        return nis;
    }

    public void setNis(String nis) {
        this.nis = nis;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }
}
