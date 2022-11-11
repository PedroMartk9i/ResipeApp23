package com.example.resipeapp.model.entities;

import java.io.Serializable;

public class Sugerencias implements Serializable {

    private String sugerencias, calificacion;

    public Sugerencias(String sugerencias, String calificacion) {
        this.sugerencias = sugerencias;
        this.calificacion = calificacion;
    }

    //constructor vacio
    public Sugerencias(String sugerencias) {

    }

    public String getSugerencias() {
        return sugerencias;
    }

    public void setSugerencias(String sugerencias) {
        this.sugerencias = sugerencias;
    }

    public String getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(String calificacion) {
        this.calificacion = calificacion;
    }
}
