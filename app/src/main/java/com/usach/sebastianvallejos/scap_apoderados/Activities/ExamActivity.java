package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class ExamActivity extends AppCompatActivity {

    //Variables a utilizar
    private Intent intent;
    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Alumnos alumno = new Alumnos();
    private LinearLayout exams_layout;
    private LinearLayout exams_fechas_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        intent = getIntent();

        exams_layout = (LinearLayout) findViewById(R.id.exams_layout);
        exams_fechas_layout = (LinearLayout) findViewById(R.id.exams_fechas_layout);

        crearAlumno();
    }

    private void crearAlumno()
    {
        alumno.setId(intent.getStringExtra("id").toString());
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

                if(actividad.getTipo().equals("Pruebas"))
                {
                    crearInformacionVista(actividad);
                }else{

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
        exams_layout.addView(botonVista,params);

        //Se crea un TextView para rellenarlo con la fecha
        TextView textoFecha = new TextView(this);
        textoFecha.setText(actividad.getFecha());
        params.gravity = Gravity.CENTER_HORIZONTAL;
        textoFecha.setTextColor(Color.parseColor("#FFFFFF"));

        //SE NECESITA CAMBIAR A DP
        textoFecha.setTextSize(35);

        exams_fechas_layout.addView(textoFecha,params);
    }

    private void setearListener(Button boton, final Actividad actividad)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Desde que vista hasta que vista llegar
                Intent intent = new Intent(ExamActivity.this,DetailExamActivity.class);

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
