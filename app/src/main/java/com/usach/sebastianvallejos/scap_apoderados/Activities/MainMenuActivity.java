package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.usach.sebastianvallejos.scap_apoderados.Models.Alumnos;
import com.usach.sebastianvallejos.scap_apoderados.R;

public class MainMenuActivity extends AppCompatActivity {

    //Definicion de variables a utilizar
    private ImageButton botonSes;
    private ImageButton botonHomework;
    private ImageButton botonMaterials;
    private ImageButton botonExams;
    private Alumnos alumnoActual = new Alumnos();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        intent = getIntent();

        //Se obtienen los datos del alumno
        crearAlumno();

        //Se asignan botones desde la vista mediante el ID del boton
        botonSes = (ImageButton) findViewById(R.id.boton_ses);
        botonExams = (ImageButton) findViewById(R.id.boton_pruebas);
        botonHomework = (ImageButton) findViewById(R.id.boton_homework);
        botonMaterials = (ImageButton) findViewById(R.id.boton_materials);

        //Se activan los botones para que tengan una accion
        activarBotonSes(botonSes);
        activarBotonExams(botonExams);
        activarBotonHomework(botonHomework);
        activarBotonMaterials(botonMaterials);

    }

    private void crearAlumno()
    {
        alumnoActual.setId(intent.getStringExtra("id"));
        alumnoActual.setNombre(intent.getStringExtra("nombre"));
        alumnoActual.setColegio(intent.getStringExtra("colegio"));
        alumnoActual.setSeccion(intent.getStringExtra("seccion"));
        alumnoActual.setApellidoPaterno(intent.getStringExtra("apellidoPaterno"));
        alumnoActual.setApellidoMaterno(intent.getStringExtra("apellidoMaterno"));
    }

    //Aqui se le dice al boton SES donde debe dirigirse una vez pulsado y se le entregan algunos atributos
    private void activarBotonSes(ImageButton boton)
    {

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this , HomeworkActivity.class);

                try
                {
                    intent.putExtra("id",alumnoActual.getId());
                    intent.putExtra("nombre",alumnoActual.getNombre());
                    intent.putExtra("aPaterno",alumnoActual.getApellidoPaterno());
                    intent.putExtra("aMaterno",alumnoActual.getApellidoMaterno());
                    intent.putExtra("seccion",alumnoActual.getSeccion());
                    intent.putExtra("colegio",alumnoActual.getColegio());


                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Aqui se le dice al boton Exams donde debe dirigirse una vez pulsado y se le entregan algunos atributos
    private void activarBotonExams(ImageButton boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this , ExamActivity.class);

                try
                {
                    intent.putExtra("id",alumnoActual.getId());
                    intent.putExtra("nombre",alumnoActual.getNombre());
                    intent.putExtra("aPaterno",alumnoActual.getApellidoPaterno());
                    intent.putExtra("aMaterno",alumnoActual.getApellidoMaterno());
                    intent.putExtra("seccion",alumnoActual.getSeccion());
                    intent.putExtra("colegio",alumnoActual.getColegio());

                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Aqui se le dice al boton Homework donde debe dirigirse una vez pulsado y se le entregan algunos atributos
    private void activarBotonHomework(ImageButton boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this , HomeworkActivity.class);

                try
                {
                    //Se le pasan los parametros necesarios a la siguiente activity
                    intent.putExtra("id",alumnoActual.getId());
                    intent.putExtra("nombre",alumnoActual.getNombre());
                    intent.putExtra("aPaterno",alumnoActual.getApellidoPaterno());
                    intent.putExtra("aMaterno",alumnoActual.getApellidoMaterno());
                    intent.putExtra("seccion",alumnoActual.getSeccion());
                    intent.putExtra("colegio",alumnoActual.getColegio());

                    //Se inicia la siguiente activity
                    startActivity(intent);
                    finish();
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Aqui se le dice al boton Materials donde debe dirigirse una vez pulsado y se le entregan algunos atributos
    private void activarBotonMaterials(ImageButton boton)
    {
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this , MaterialActivity.class);

                try
                {
                    intent.putExtra("id",alumnoActual.getId());
                    intent.putExtra("nombre",alumnoActual.getNombre());
                    intent.putExtra("aPaterno",alumnoActual.getApellidoPaterno());
                    intent.putExtra("aMaterno",alumnoActual.getApellidoMaterno());
                    intent.putExtra("seccion",alumnoActual.getSeccion());
                    intent.putExtra("colegio",alumnoActual.getColegio());

                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
