package com.example.resipeapp.model.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.resipeapp.model.entities.Recetas;

import java.util.List;

//declaramos el DAO (DATA ACCESS OBJECT)
@Dao
public interface RecetasDAO {

    @Query("select * from recetas")
    List<Recetas> obtenerTodo(); //traer la tabla de la informacion

    @Insert
    void insertarElemento(Recetas miReceta);

    @Update
    void editar(Recetas miReceta);

    @Delete
    void eliminar(Recetas miReceta);

    @Query("select * from recetas where identificador=:parametro") //este parametro sirve para obtener
    Recetas obtenerPorIdentificador(int parametro);

}
