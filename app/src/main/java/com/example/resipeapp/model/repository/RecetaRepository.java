package com.example.resipeapp.model.repository;

import android.content.Context;

import com.example.resipeapp.model.entities.Recetas;
import com.example.resipeapp.model.local.BaseDatos;
import com.example.resipeapp.model.local.RecetasDAO;

import java.util.List;

public class RecetaRepository {

    //Creamos el atributo para el constructor
    RecetasDAO recetasDAO;

    //Generamos el constructor
    public RecetaRepository(Context miContext) {
        BaseDatos miBaseDatos = BaseDatos.obtenerInstacia(miContext); //no lo traemos
        this.recetasDAO = miBaseDatos.recetasDAO();
    }

    //creamos modelos a utilizar
    public List<Recetas> obtenerTodasRecetas(){
        return recetasDAO.obtenerTodo(); //mientras hacemos lógica
    }

    //insertamos metodos de insertar,actualizar,eliminar y consultar
    public void insertarReceta(Recetas miReceta){
        recetasDAO.insertarElemento(miReceta);
    }

    public void actualizarReceta(Recetas miReceta){
        recetasDAO.editar(miReceta);
    }

    public void eliminarReceta(Recetas miReceta){
        recetasDAO.eliminar(miReceta);
    }

    public Recetas consultarSeriePorId(int identificador){
        return recetasDAO.obtenerPorIdentificador(identificador);
    }

}
