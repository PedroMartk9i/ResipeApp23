package com.example.resipeapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.resipeapp.model.local.BaseDatos;
import com.example.resipeapp.R;
import com.example.resipeapp.model.entities.Recetas;
import com.example.resipeapp.model.local.RecetasDAO;
import com.example.resipeapp.model.repository.RecetaRepository;
import com.google.firebase.firestore.FirebaseFirestore;

public class FormularioActivity extends AppCompatActivity {

    //Declaramos la coleccion de firebase con el nombre que creamos en web(google FireBase)
    public static final String NOMBRE_COLECCION = "recetas";

    //Declaramos los EditText y el Button de los xml
    private EditText etnomreceta, etpreparacion, eturl;
    private Button btnguardar;

    //declaramos la Receta Repository para trabajar con Room
    private RecetaRepository miRecetaRepository;


    //Accion de hacer click y retroceder con Override Methods:onSupportNavigateUp():boolean
    @Override
    public boolean onSupportNavigateUp() {
        //Comentamos lo que trae por defecto
        //return super.onSupportNavigateUp();
        //Creamos la flecha de regresar por medio de onBackPressed
        onBackPressed();
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        //Cambiamos Titulo de la Activity
        this.setTitle("Agregar Receta");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Inicializamos miRecetaRepository por medio de un contructor y le pasamos getApplicationContext()
        miRecetaRepository = new RecetaRepository(getApplicationContext());

        //inicializamos el metodo privado que llamamos referenciardatos
        referenciardatos();
    }

    //Creamos método privado referenciardatos
    private void referenciardatos(){
        //referenciamos cada cosa del xml
        etnomreceta = findViewById(R.id.et_nomreceta);
        etpreparacion = findViewById(R.id.et_preparacion);
        eturl = findViewById(R.id.et_url);
        btnguardar = findViewById(R.id.btn_guardar);
    }

    //Creamos método publico clickguardar en donde atrapamos los datos
    public void clickguardar(View view){
        String nombre = etnomreceta.getText().toString();
        String preparacion = etpreparacion.getText().toString();
        String url = eturl.getText().toString();

        //Hacemos validacion de campos vacios para que la persona no envie campos en blanco
        //Lo realizamos con cada campo
        if("".equals(nombre)){
            etnomreceta.setError(getString(R.string.error_validacion));
            return;
        }
        if("".equals(preparacion)){
            etpreparacion.setError(getString(R.string.error_validacion));
            return;
        }
        if("".equals(url)){
            eturl.setError(getString(R.string.error_validacion));
            return;
        }

        //constructor
        Recetas recetas = new Recetas(nombre,preparacion,url);

        //Creamos un intent para los datos
        Intent datos = new Intent();
        datos.putExtra("datos_de_receta",recetas);

        setResult(RESULT_OK,datos);

        /*BaseDatos miBaseDatos = BaseDatos.obtenerInstacia(FormularioActivity.this);
        RecetasDAO recetasDAO = miBaseDatos.recetasDAO();

        recetasDAO.insertarElemento(recetas);*/

        //Implementamos Base de datos Firebase
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(NOMBRE_COLECCION).add(recetas);

        //sirve para insertar la nueva Serie en room
        //miRecetaRepository.insertarReceta(recetas);


        finish();

    }

    /*public void clickregresar(View view){
        Intent intent = new Intent(FormularioActivity.this, MainActivity.class);
        startActivity(intent);
        //Intent intent = new Intent(MainActivity.this,FormularioActivity.class);
        //irFormulario.launch(intent);
    }*/

}