package com.example.resipeapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.resipeapp.R;
import com.example.resipeapp.model.entities.Registro;
import com.example.resipeapp.model.entities.Sugerencias;
import com.google.firebase.firestore.FirebaseFirestore;

public class SugerenciaActivity extends AppCompatActivity {

    //Declaramos la coleccion de firebase con el nombre que creamos en web(google FireBase)
    public static final String NOMBRE_COLECCION3 = "sugerencias";

    //Declaramos
    private EditText etsugerencia, etcalificacion;

    @Override
    public boolean onSupportNavigateUp() {
        //return super.onSupportNavigateUp();
        onBackPressed();
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugerencia);

        this.setTitle("Sugerencias");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //referenciamos campo
        etsugerencia = findViewById(R.id.et_sugerencia);
        etcalificacion = findViewById(R.id.et_calificacion);
    }

    public void clickSugerencia(View view){
        String sug = etsugerencia.getText().toString();
        String rking = etcalificacion.getText().toString();

        //Hacemos validacion de campos vacios para que la persona no envie campos en blanco
        //Lo realizamos con cada campo
        if("".equals(sug)){
            etsugerencia.setError(getString(R.string.error_validacion));
            return;
        }
        if("".equals(rking)){
            etcalificacion.setError(getString(R.string.error_validacion));
            return;
        }

        //constructor
        Sugerencias sugerencia = new Sugerencias(sug, rking);

        Intent datos = new Intent();
        //datos.putExtra("datos_de_registro",recetas);

        setResult(RESULT_OK,datos);

        //Implementamos Base de datos Firebase
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(NOMBRE_COLECCION3).add(sugerencia);

        finish();

    }
}