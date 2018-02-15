package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.usach.sebastianvallejos.scap_apoderados.Models.Alumnos;
import com.usach.sebastianvallejos.scap_apoderados.R;

public class MainMenuActivity extends AppCompatActivity {

    //Definicion de variables a utilizar
    private ImageButton botonSes;
    private ImageButton botonHomework;
    private ImageButton botonMaterials;
    private ImageButton botonExams;
    private Alumnos alumnoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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

    //Aqui se le dice al boton SES donde debe dirigirse una vez pulsado y se le entregan algunos atributos
    private void activarBotonSes(ImageButton boton)
    {

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this , SESActivity.class);

                try
                {
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
                    startActivity(intent);
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
