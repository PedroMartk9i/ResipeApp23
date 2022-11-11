package com.example.resipeapp.view.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.resipeapp.model.local.BaseDatos;
import com.example.resipeapp.R;
import com.example.resipeapp.model.entities.Recetas;
import com.example.resipeapp.model.local.RecetasDAO;
import com.example.resipeapp.model.repository.RecetaRepository;
import com.example.resipeapp.view.adapter.AdaptadorRecetas;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private SearchView txtBuscar;
    private RecyclerView rvRecetas;
    private ArrayList<Recetas> listadoRecetas;
    public AdaptadorRecetas adaptador;

    //Declaramos
    private RecetaRepository miRecetaRepository;

    //declaramos la coleccion de firebase con el nombre que creamos en Firebase Web
    public static final String NOMBRE_COLECCION = "recetas";
    public static final String NOMBRE_COLECCION2 = "registro";

    //
    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.menu_navbar, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Cerrar_sesion:
                SharedPreferences sharedPreferences = getSharedPreferences("preferencias",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit(); //nos traemos el editor tal cual del loginactivity
                editor.putBoolean("logueado",false);//nos traemos el editor tal cual del loginactivity y le cambiamos el true a false
                editor.apply();//nos traemos el editor tal cual del loginactivity

                Intent in = new Intent(MainActivity.this,LoginActivity.class);//este nos sirve para mandar a la activity que deseemos despues de poner los datos
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );//le pasamos la bandera para que cierre todoydeje el login
                startActivity(in);

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creamos el constructor para asegurarnos de que lo tenga toda la activity
        // inicializamos el RecetaRepository, lo hacemos por medio de un constructor
        miRecetaRepository = new RecetaRepository(getApplicationContext());

        //Declaramos el SearchView
        //txtBuscar = findViewById(R.id.txt_buscar);

        listadoRecetas = new ArrayList<>();

        //cargarDatosInciales();
        //Inicializamos los Datos de las recetas que lo llame: cargarDatosBasesDatos();
        cargarDatosBasesDatos();
        cargarDatosBasesDatosRegistro();


        //Nos traemos el adaptador de recetas y le pasamos el listado de recetas
         adaptador = new AdaptadorRecetas(listadoRecetas);

        //Creamos el método onItemClick y nos crea por defecto el "@Override"
        adaptador.setOnItemClickListener(new AdaptadorRecetas.OnItemClickListener() {
            @Override
            public void onItemClick(Recetas recetas, int posicion) {
                //Funciona para que pase del MainActivity al FormularioActivity
                Intent intent2 = new Intent(MainActivity.this, FormularioActivity.class);
                //inicializamos el intent
                startActivity(intent2);
                //Declaramos Toast para sirve para mostrar
                Toast.makeText(MainActivity.this, "Seleccionado " + recetas.getNombre(), Toast.LENGTH_SHORT).show();
                //Toast.makeText(MainActivity.this, irFormulario.launch(startActivity();), Toast.LENGTH_SHORT).show();
                //Toast.makeText(Intent intent = new Intent(MainActivity.this,FormularioActivity.class);


            }
        });

        //txtBuscar.setOnQueryTextListener(this);

        rvRecetas = findViewById(R.id.rv_listado);
        //rvRecetas.setLayoutManager(new LinearLayoutManager(this)); para ver de uno en uno
        rvRecetas.setLayoutManager(new GridLayoutManager(this, 2));//para ver de dos en dos
        rvRecetas.setAdapter(adaptador);
    }

    private void cargarDatosBasesDatos() {

        //Hacemos peticion asincronica
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(NOMBRE_COLECCION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //task es tarea
                //validamos si la tarea esta completada
                if (task.isSuccessful()){
                    //limpiar listado de series
                    listadoRecetas.clear();
                    //regresamos documentos
                    for (DocumentSnapshot document : task.getResult()){
                        Recetas misRecetas = document.toObject(Recetas.class);
                        listadoRecetas.add(misRecetas);
                    }

                    //actualizamos el adaptador de Recetas y le ponga el nuevo listado
                    adaptador.setListadoRecetas(listadoRecetas);

                }else{
                    Log.e("errorFS",task.getException().getMessage());
                }

            }
        });

        /*BaseDatos miBaseDatos = BaseDatos.obtenerInstacia(MainActivity.this);
        RecetasDAO recetasDAO = miBaseDatos.recetasDAO();

        //lo casteamos y convertimos la list a arrayList
        listadoRecetas = (ArrayList<Recetas>) recetasDAO.obtenerTodo();*/



        //casteamos la lista de una lista normal a un arraylist
        //listadoRecetas = (ArrayList<Recetas>) miRecetaRepository.obtenerTodasRecetas();//instanciamos metodos de obtener del RecetaRepository

        /*if (listadoRecetas.isEmpty()) {
            creoPorDefecto();
        }*/

    }

    private void cargarDatosBasesDatosRegistro() {

        //Hacemos peticion asincronica
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection(NOMBRE_COLECCION2).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //task es tarea
                //validamos si la tarea esta completada
                if (task.isSuccessful()){
                    //limpiar listado de series
                    listadoRecetas.clear();
                    //regresamos documentos
                    for (DocumentSnapshot document : task.getResult()){
                        Recetas misRecetas = document.toObject(Recetas.class);
                        listadoRecetas.add(misRecetas);
                    }

                    //actualizamos el adaptador de Recetas y le ponga el nuevo listado
                    adaptador.setListadoRecetas(listadoRecetas);

                }else{
                    Log.e("errorFS",task.getException().getMessage());
                }

            }
        });

    }



    private void creoPorDefecto() {

        /*BaseDatos miBaseDatos = BaseDatos.obtenerInstacia(MainActivity.this);
        RecetasDAO recetasDAO = miBaseDatos.recetasDAO();*/


        Recetas receta1 = new Recetas("Cazuela de Mariscos", "Con esta receta disgustas las delicias del mar", "https://www.goya.com/media/4126/cazuela-de-mariscos_spanish-style-shellfish-stew.jpg?quality=80");
        Recetas receta2 = new Recetas("Sancocho de Gallina", "Con esta receta aprecias el sabor de la Gallina 100% colombiana", "https://t2.rg.ltmcdn.com/es/posts/1/8/1/sancocho_de_gallina_o_pollo_12181_600_square.jpg");
        Recetas receta3 = new Recetas("Costillas de Res en Salsa BBQ", "Las costillas más jugosas, suaves " +
                "y deliciosas que pueden probar! Y una salsa BBQ hecha en casa para chuparse los dedos! Para prepararlas, " +
                "solo hay que seguir el paso a paso!", "https://antojandoando.com/wp-content/uploads/2022/07/Cost-BBQ-pintplato-1.jpg");
        Recetas receta4 = new Recetas("Pollo Dorado en Salsa Agridulce", "Díganme si solo con mirar este pollo en " +
                "salsa no se les antoja? Es un pollo realmente exquisito, con un color increíble! Además, lleva una salsa con un " +
                "toque asiático ácido, dulce, picante, aromático, una mezcla que realmente le va de maravilla!",
                "https://antojandoando.com/wp-content/uploads/2022/07/pollo-asia-pint.jpg");
        Recetas receta5 = new Recetas("Pavo al Horno", "En Estados Unidos y Canadá, es tradición preparar " +
                "el pavo para celebrar el Día de Acción de Gracias (Thanksgiving). Algunos pavos van rellenos y se sirven " +
                "con una salsa de arándanos (cranberry sauce). También se hacen platos en diferentes preparaciones para " +
                "acompañar como habichuelas (green beans, judías verdes, ejotes), papa dulce (camote, boniato), puré de " +
                "papa con gravy (salsa hecha a partir de la sustancia del pavo) y gran variedad de postres y bebidas.",
                "https://antojandoando.com/wp-content/uploads/2015/10/pavo-pint-pq.jpg");

        Recetas receta6 = new Recetas("Arroz con Pollo", "Delicia de receta", "https://antojandoando.com/wp-content/uploads/2015/01/arroz-pollo2-pint-pq.jpg");

        miRecetaRepository.insertarReceta(receta1);
        miRecetaRepository.insertarReceta(receta2);
        miRecetaRepository.insertarReceta(receta3);
        miRecetaRepository.insertarReceta(receta4);
        miRecetaRepository.insertarReceta(receta5);
        miRecetaRepository.insertarReceta(receta6);

        /*recetasDAO.insertarElemento(receta1);
        recetasDAO.insertarElemento(receta2);
        recetasDAO.insertarElemento(receta3);
        recetasDAO.insertarElemento(receta4);
        recetasDAO.insertarElemento(receta5);
        recetasDAO.insertarElemento(receta6);*/

        listadoRecetas = (ArrayList<Recetas>) miRecetaRepository.obtenerTodasRecetas();
    }

    private void cargarDatosInciales() {

        listadoRecetas = new ArrayList<>();
        Recetas receta1 = new Recetas("Cazuela de Mariscos", "Con esta receta disgustas las delicias del mar", "https://www.goya.com/media/4126/cazuela-de-mariscos_spanish-style-shellfish-stew.jpg?quality=80");
        Recetas receta2 = new Recetas("Sancocho de Gallina", "Con esta receta aprecias el sabor de la Gallina 100% colombiana", "https://t2.rg.ltmcdn.com/es/posts/1/8/1/sancocho_de_gallina_o_pollo_12181_600_square.jpg");
        Recetas receta3 = new Recetas("Costillas de Res en Salsa BBQ", "Las costillas más jugosas, suaves " +
                "y deliciosas que pueden probar! Y una salsa BBQ hecha en casa para chuparse los dedos! Para prepararlas, " +
                "solo hay que seguir el paso a paso!", "https://antojandoando.com/wp-content/uploads/2022/07/Cost-BBQ-pintplato-1.jpg");
        Recetas receta4 = new Recetas("Pollo Dorado en Salsa Agridulce", "Díganme si solo con mirar este pollo en " +
                "salsa no se les antoja? Es un pollo realmente exquisito, con un color increíble! Además, lleva una salsa con un " +
                "toque asiático ácido, dulce, picante, aromático, una mezcla que realmente le va de maravilla!",
                "https://antojandoando.com/wp-content/uploads/2022/07/pollo-asia-pint.jpg");
        Recetas receta5 = new Recetas("Pavo al Horno", "En Estados Unidos y Canadá, es tradición preparar " +
                "el pavo para celebrar el Día de Acción de Gracias (Thanksgiving). Algunos pavos van rellenos y se sirven " +
                "con una salsa de arándanos (cranberry sauce). También se hacen platos en diferentes preparaciones para " +
                "acompañar como habichuelas (green beans, judías verdes, ejotes), papa dulce (camote, boniato), puré de " +
                "papa con gravy (salsa hecha a partir de la sustancia del pavo) y gran variedad de postres y bebidas.",
                "https://antojandoando.com/wp-content/uploads/2015/10/pavo-pint-pq.jpg");

        Recetas receta6 = new Recetas("Arroz con Pollo", "Delicia de receta", "https://antojandoando.com/wp-content/uploads/2015/01/arroz-pollo2-pint-pq.jpg");


        listadoRecetas.add(receta1);
        listadoRecetas.add(receta2);
        listadoRecetas.add(receta3);
        listadoRecetas.add(receta4);
        listadoRecetas.add(receta5);
        listadoRecetas.add(receta6);

    }

    public void clickirformulario(View view) {
        Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
        irFormulario.launch(intent);
    }

    public void clickirSugerencia(View view){
        Intent in = new Intent(MainActivity.this, SugerenciaActivity.class);
        startActivity(in);
    }

    ActivityResultLauncher<Intent> irFormulario = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == RESULT_OK) ;
            {

                /*Recetas recetaatrapada = (Recetas) result.getData().getSerializableExtra("datos_de_receta");

                listadoRecetas.add(recetaatrapada);*/
                cargarDatosBasesDatos();
                adaptador.setListadoRecetas(listadoRecetas);
            }

        }
    });


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adaptador.filtrado(newText);
        return false;
    }
}