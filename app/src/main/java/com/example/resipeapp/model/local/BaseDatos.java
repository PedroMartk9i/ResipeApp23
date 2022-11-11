package com.example.resipeapp.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.resipeapp.model.entities.Recetas;

//abstracta significa que no se puede instanciar, por ejemplo con new, etc
//colocamos el abstract y la extencion RoomDatabase
//creamos la instancia
// "@Database(entities = {Recetas.class},version = 1)"
@Database(entities = {Recetas.class},version = 1)
public abstract class BaseDatos extends RoomDatabase {
    public abstract RecetasDAO recetasDAO();
    private static BaseDatos instancia = null;

    public static BaseDatos obtenerInstacia(Context miContexto){
        if (instancia==null){
            instancia = Room.databaseBuilder(miContexto,BaseDatos.class,"resipeapp.db").allowMainThreadQueries().build();
        }
        return instancia;
    }


}
