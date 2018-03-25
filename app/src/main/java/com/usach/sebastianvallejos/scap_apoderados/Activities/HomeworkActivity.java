package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usach.sebastianvallejos.scap_apoderados.Models.Actividad;
import com.usach.sebastianvallejos.scap_apoderados.Models.Alumnos;
import com.usach.sebastianvallejos.scap_apoderados.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class HomeworkActivity extends AppCompatActivity {

    //Definicion de variables a utilizar
    private Intent intent;
    final FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Alumnos alumnoActual = new Alumnos();
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);
        intent = getIntent();

        //Obtenemos la fecha actual
        Date tiempo = Calendar.getInstance().getTime();
        fecha = new SimpleDateFormat("yyyy/MM/dd").format(tiempo);

        //Recuperamos los datos de la actividad anterior y creamos un alumno
        crearAlumno(intent);

        //Obtenemos las actividades desde la base de datos Firebase
        obtenerActividades(alumnoActual.getColegio(),alumnoActual.getSeccion());


    }

    //Obtenemos los datos guardados anteriormente
    private void crearAlumno(Intent in)
    {
        alumnoActual.setId(in.getStringExtra("id"));
        alumnoActual.setNombre(in.getStringExtra("nombre"));
        alumnoActual.setSeccion(in.getStringExtra("seccion"));
        alumnoActual.setColegio(in.getStringExtra("colegio"));
        alumnoActual.setApellidoPaterno(in.getStringExtra("aPaterno"));
        alumnoActual.setApellidoMaterno(in.getStringExtra("aMaterno"));
    }

    //Funcion encargada de obtener todas las actividades para un colegio y seccion en especifico
    private void obtenerActividades(String colegio, String seccion)
    {

        DatabaseReference actividadesRef = mDataBase.getReference(colegio);

        actividadesRef.child("fechas").orderByChild("seccion").equalTo(seccion).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                //Por cada actividad encontrada, se guarda en una lista de elementos "Actividad"
                Actividad actividad = dataSnapshot.getValue(Actividad.class);

                Log.i("PRUEBA","Se ha encontrado una actividad la cual es:" +actividad.getTipo());
                Log.i("PRUEBA","Se ha obtenido una actividad con la siguiente fecha: "+actividad.getFecha());
                Log.i("PRUEBA","La fecha actual es: "+fecha);
                Log.i("PRUEBA","La comparacion entre ambas es: "+actividad.getFecha().compareTo(fecha));

                //Vemos si la actividad es una prueba y si la fecha actual no es mayor que la fecha de la actividad
                if(actividad.getTipo().equals("Tarea") && (actividad.getFecha().compareTo(fecha) != -1))
                {
                    Log.i("PRUEBA","Entra y crea una actividad");
                    crearInformacionTareas(actividad);
                }
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
    private void crearInformacionTareas(Actividad actividad)
    {
        //Primero ubicamos los LinearLayouts correspondiente a los lugares donde se entregara la informacion
        LinearLayout layoutBoton = (LinearLayout) findViewById(R.id.materia_tareas);
        LinearLayout layoutFecha = (LinearLayout) findViewById(R.id.fecha_tareas);

        //Parametros necesarios para colocar los elementos nuevso en la vista
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //Se crea el boton y se le asigna un Listener
        Button botonVista = new Button(this);
        botonVista.setText(actividad.getMateria());
        setearListener(botonVista,actividad);
        layoutBoton.addView(botonVista,params);

        //Se crea un TextView para rellenarlo con la fecha
        TextView textoFecha = new TextView(this);
        textoFecha.setText(actividad.getFecha());
        params.gravity = Gravity.CENTER_HORIZONTAL;
        textoFecha.setTextColor(Color.parseColor("#FFFFFF"));

        //SE NECESITA CAMBIAR A DP
        textoFecha.setTextSize(30);

        layoutFecha.addView(textoFecha,params);


    }

    //Listener para que cada boton tenga una accion
    private void setearListener(Button boton, final Actividad actividad)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Desde que vista hasta que vista llegar
                Intent intent = new Intent(HomeworkActivity.this,DetailHomeworkActivity.class);

                try {

                    intent.putExtra("profesor", actividad.getProfesor());
                    intent.putExtra("materia", actividad.getMateria());
                    intent.putExtra("fecha", actividad.getFecha());
                    intent.putExtra("descripcion", actividad.getDescripcion());

                    startActivity(intent);

                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }

}
