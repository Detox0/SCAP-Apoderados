package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Calendar;
import java.util.Date;

public class MaterialActivity extends AppCompatActivity {

    //Variables a utilizar
    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Intent intent;
    private Alumnos alumno = new Alumnos();
    private String fecha;
    private LinearLayout materiales_layout;
    private LinearLayout materiales_fechas_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);

        materiales_layout = (LinearLayout) findViewById(R.id.materiales_layout);
        materiales_fechas_layout = (LinearLayout) findViewById(R.id.materiales_fechas_layout);

        //Obtenemos la fecha actual
        Date tiempo = Calendar.getInstance().getTime();
        fecha = new SimpleDateFormat("yyyy/MM/dd").format(tiempo);

        intent = getIntent();

        crearAlumno();
    }

    private void crearAlumno()
    {
        alumno.setId(intent.getStringExtra("id"));
        alumno.setNombre(intent.getStringExtra("nombre"));
        alumno.setSeccion(intent.getStringExtra("seccion"));
        alumno.setColegio(intent.getStringExtra("colegio"));
        alumno.setApellidoPaterno(intent.getStringExtra("aPaterno"));
        alumno.setApellidoMaterno(intent.getStringExtra("aMaterno"));

        obtenerActividades(alumno.getColegio(),alumno.getSeccion());
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

                //Vemos si la actividad es una prueba y si la fecha actual no es mayor que la fecha de la actividad
                if(actividad.getTipo().equals("Materiales") && (actividad.getFecha().compareTo(fecha) != -1))
                {
                    crearInformacionVista(actividad);
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

    private void crearInformacionVista(Actividad actividad)
    {
        //Parametros necesarios para colocar los elementos nuevso en la vista
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //Se crea el boton y se le asigna un Listener
        Button botonVista = new Button(this);
        botonVista.setText(actividad.getMateria());
        setearListener(botonVista,actividad);
        materiales_layout.addView(botonVista,params);

        //Se crea un TextView para rellenarlo con la fecha
        TextView textoFecha = new TextView(this);
        textoFecha.setText(actividad.getFecha());
        params.gravity = Gravity.CENTER_HORIZONTAL;
        textoFecha.setTextColor(Color.parseColor("#FFFFFF"));

        //SE NECESITA CAMBIAR A DP
        textoFecha.setTextSize(30);

        materiales_fechas_layout.addView(textoFecha,params);
    }

    private void setearListener(Button boton, final Actividad actividad)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Desde que vista hasta que vista llegar
                Intent intent = new Intent(MaterialActivity.this,DetailMaterialActivity.class);

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
