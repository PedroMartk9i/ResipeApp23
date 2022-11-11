package com.example.resipeapp.model.entities;

import java.io.Serializable;

public class Registro implements Serializable {

    private String nombreuser, email, password;

    public Registro(String nombreuser, String email, String password) {
        this.nombreuser = nombreuser;
        this.email = email;
        this.password = password;
    }

    //constructor vacio
    public Registro(String registro) {

    }

    //getters and setters
    public String getNombreuser() {
        return nombreuser;
    }

    public void setNombreuser(String nombreuser) {
        this.nombreuser = nombreuser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
