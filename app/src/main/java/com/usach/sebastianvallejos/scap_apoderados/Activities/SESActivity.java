package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
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
import com.google.firebase.database.ValueEventListener;
import com.usach.sebastianvallejos.scap_apoderados.Models.Alumnos;
import com.usach.sebastianvallejos.scap_apoderados.Models.Answers;
import com.usach.sebastianvallejos.scap_apoderados.Models.Ses;
import com.usach.sebastianvallejos.scap_apoderados.R;


public class SESActivity extends AppCompatActivity {

    //Variables necesarias
    private LinearLayout ses_layout;
    private LinearLayout ses_fechas_layout;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private Intent intent;
    private String idPadre;
    private Alumnos alumno = new Alumnos();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ses);
        intent = getIntent();

        ses_layout = (LinearLayout) findViewById(R.id.ses_layout);
        ses_fechas_layout = (LinearLayout) findViewById(R.id.ses_fechas_layout);

        crearAlumno();
    }

    private void crearAlumno()
    {
        idPadre = intent.getStringExtra("idPadre");
        alumno.setId(intent.getStringExtra("id"));
        alumno.setSeccion(intent.getStringExtra("seccion"));
        alumno.setColegio(intent.getStringExtra("colegio"));
        alumno.setApellidoPaterno(intent.getStringExtra("aPaterno"));
        alumno.setApellidoMaterno(intent.getStringExtra("aMaterno"));
        alumno.setNombre(intent.getStringExtra("nombre"));
        obtenerActividadSes();
    }

    private void obtenerActividadSes()
    {
        DatabaseReference sesRef = mDatabase.getReference(alumno.getColegio());

        sesRef.child("ses").orderByChild("seccion").equalTo(alumno.getSeccion()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Ses actSes = dataSnapshot.getValue(Ses.class);

                actSes.setId(dataSnapshot.getKey());

                //Verificamos si el apoderado ya respondió esta pregunta
                verificarActividadSes(actSes);
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

    private void crearBoton(Ses actividad)
    {
        //Parametros necesarios para colocar los elementos nuevso en la vista
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        //Se crea el boton y se le asigna un Listener
        Button botonVista = new Button(this);
        botonVista.setText(actividad.getMateria());
        setearListener(actividad, botonVista);
        ses_layout.addView(botonVista,params);

        //Se crea un TextView para rellenarlo con la fecha
        TextView textoFecha = new TextView(this);
        textoFecha.setText(actividad.getFecha());
        params.gravity = Gravity.CENTER_HORIZONTAL;
        textoFecha.setTextColor(Color.parseColor("#FFFFFF"));

        //SE NECESITA CAMBIAR A DP
        textoFecha.setTextSize(30);

        ses_fechas_layout.addView(textoFecha,params);
    }

    private void setearListener(final Ses actividad, Button boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Desde que vista hasta que vista llegar
                Intent intent = new Intent(SESActivity.this,DetailSESActivity.class);

                try {
                    intent.putExtra("idPadre", idPadre);
                    intent.putExtra("nombre", alumno.getNombre());
                    intent.putExtra("apellido_paterno_alumno", alumno.getApellidoPaterno());
                    intent.putExtra("apellido_materno_alumno",alumno.getApellidoMaterno());
                    intent.putExtra("id", actividad.getId());
                    intent.putExtra("profesor", actividad.getProfesor());
                    intent.putExtra("materia", actividad.getMateria());
                    intent.putExtra("fecha", actividad.getFecha());
                    intent.putExtra("descripcion", actividad.getDescripcion());
                    intent.putExtra("colegio",alumno.getColegio());
                    intent.putExtra("seccion", alumno.getSeccion());
                    intent.putExtra("idAlumno", alumno.getId());

                    startActivity(intent);
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    //Revisar esta funcion
    private void verificarActividadSes(final Ses actividad)
    {
        DatabaseReference verificarRef = mDatabase.getReference(alumno.getColegio());

        //Consultamos en la BD si el ID del apoderado ya se encuentra en la lista de apoderados que hicieron la actividad
        verificarRef.child("ses").child(actividad.getId()).child("respuestas").orderByChild("idApoderado").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean verificador = true;

                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    Answers resp = data.getValue(Answers.class);

                    if(resp.getIdApoderado().equals(idPadre))
                    {
                        verificador = false;
                    }
                }

                if(verificador)
                {
                    Log.i("PRUEBA","No se ha realizado la actividad.");
                    crearBoton(actividad);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
