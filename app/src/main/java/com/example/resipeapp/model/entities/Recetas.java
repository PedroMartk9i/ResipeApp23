package com.example.resipeapp.model.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.PropertyName;

import java.io.Serializable;

//declaramos la clase @entity y se impota por defecto "import androidx.room.Entity;"
@Entity
public class Recetas implements Serializable {

    //creamos la llaveprimaria @Primarykey y se importa por defecto "import androidx.room.PrimaryKey;"
    //autogenerate es el que le pone los ids
    @PrimaryKey(autoGenerate = true)
    private int identificador;//creamos identificador

    //ponemos nombres a las tablas para asegurarnos
    @ColumnInfo(name = "nombre")
    private String nombre;

    //ponemos nombres a las tablas para asegurarnos
    @ColumnInfo(name = "descripcion")
    private String descripcion;

    //cambiamos nombre, porque debe estar en minuscula
    @ColumnInfo(name = "url_imagen")
    private String urlImagen;

    //constructor
    public Recetas(String nombre, String descripcion, String urlImagen) {
        this.identificador = 0; //declaramos la posicion "0" que equivale a la primera
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.urlImagen = urlImagen;
    }

     //constructor vacio
    public Recetas() {
        this.identificador = 0;
    }

    //getters and setters
    //excluimos el identificador
    @Exclude
    public int getIdentificador() {
        return identificador;
    }

    public void setIdentificador(int identificador) {
        this.identificador = identificador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    //cambiamos el nombre en firebase con @PropertyName
    @PropertyName("url_imagen")
    public String getUrlImagen() {
        return urlImagen;
    }
    @PropertyName("url_imagen")
    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }
}
