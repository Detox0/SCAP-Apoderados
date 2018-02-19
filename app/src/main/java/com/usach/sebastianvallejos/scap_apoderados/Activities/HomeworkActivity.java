package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usach.sebastianvallejos.scap_apoderados.Models.Actividad;
import com.usach.sebastianvallejos.scap_apoderados.Models.Alumnos;
import com.usach.sebastianvallejos.scap_apoderados.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HomeworkActivity extends AppCompatActivity {

    //Definicion de variables a utilizar
    private Intent intent;
    final FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Alumnos alumnoActual = new Alumnos();
    private List<Actividad> listActividades = new ArrayList<Actividad>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        intent = getIntent();

        //Recuperamos los datos de la actividad anterior y creamos un alumno
        crearAlumno(intent);

        //Obtenemos las actividades desde la base de datos Firebase
        obtenerActividades(alumnoActual.getColegio(),alumnoActual.getSeccion());

        //Ahora procedemos a crear los botones y los textos para mostrarlos por la vista
        crearInformacionTareas();
    }

    private void crearAlumno(Intent in)
    {
        alumnoActual.setId(in.getStringExtra("id").toString());
        alumnoActual.setNombre(in.getStringExtra("nombre"));
        alumnoActual.setSeccion(in.getStringExtra("seccion"));
        alumnoActual.setColegio(in.getStringExtra("colegio"));
        alumnoActual.setApellidoPaterno(in.getStringExtra("aPaterno"));
        alumnoActual.setApellidoMaterno(in.getStringExtra("aMaterno"));
    }

    //Funcion encargada de obtener todas las actividades para un colegio y seccion en especifico
    private void obtenerActividades(String colegio, String seccion){

        DatabaseReference actividadesRef = mDataBase.getReference(colegio);

        actividadesRef.child("fechas").orderByChild("seccion").equalTo(seccion).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Por cada actividad encontrada, se guarda en una lista de elementos "Actividad"
                Actividad actividad = dataSnapshot.getValue(Actividad.class);

                listActividades.add(actividad);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    //Funcion encargada de, en base a las actividades, crear botones y textos en la vista
    private void crearInformacionTareas()
    {

        //Primero ubicamos los LinearLayouts correspondiente a los lugares donde se entregara la informacion

    }

}
