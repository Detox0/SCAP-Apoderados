package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.usach.sebastianvallejos.scap_apoderados.R;

import org.w3c.dom.Text;

public class DetailExamActivity extends AppCompatActivity {

    //Variables a utilizar
    private Intent intent;
    private TextView profesor;
    private TextView fecha;
    private TextView descripcion;
    private TextView materia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_exam);

        intent = getIntent();

        profesor = (TextView) findViewById(R.id.exams_profesor);
        fecha = (TextView) findViewById(R.id.exams_fechas);
        descripcion = (TextView) findViewById(R.id.descripcion_exams);
        materia = (TextView) findViewById(R.id.exams_materia);

        rellenarVista();
    }

    private void rellenarVista()
    {
        profesor.setText(intent.getStringExtra("profesor"));
        fecha.setText(intent.getStringExtra("fecha"));
        descripcion.setText(intent.getStringExtra("descripcion"));
        materia.setText(intent.getStringExtra("materia"));
    }
}
