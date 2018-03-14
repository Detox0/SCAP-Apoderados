package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.usach.sebastianvallejos.scap_apoderados.Models.Actividad;
import com.usach.sebastianvallejos.scap_apoderados.R;

public class DetailHomeworkActivity extends AppCompatActivity {

    //Variables a utilizar
    Intent intent;
    Actividad actividad = new Actividad();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_homework);

        intent = getIntent();

        //Recuperamos los datos entregados por la vista anterior
        crearActividad(intent);

        //Por ultimo rellenamos los datos necesarios en la vista
        rellenarActivity();

    }

    private void crearActividad(Intent in)
    {
        actividad.setDescripcion(in.getStringExtra("descripcion"));
        actividad.setFecha(in.getStringExtra("fecha"));
        actividad.setMateria(in.getStringExtra("materia"));
        actividad.setProfesor(in.getStringExtra("profesor"));
    }

    private void rellenarActivity()
    {
        //Llamamos a los textView guardados
        TextView profesor = (TextView) findViewById(R.id.detalle_tareas_profesor);
        TextView materia = (TextView) findViewById(R.id.detalle_tareas_materia);
        TextView fecha = (TextView) findViewById(R.id.detalle_tareas_fecha);
        TextView descripcion = (TextView) findViewById(R.id.descripcion_homework);

        profesor.setText(actividad.getProfesor());
        materia.setText(actividad.getMateria());
        fecha.setText(actividad.getFecha());
        descripcion.setText(actividad.getDescripcion());
    }
}
