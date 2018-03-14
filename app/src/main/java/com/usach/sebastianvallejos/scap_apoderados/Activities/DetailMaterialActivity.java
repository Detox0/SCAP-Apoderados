package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.usach.sebastianvallejos.scap_apoderados.R;

public class DetailMaterialActivity extends AppCompatActivity {

    //Variables a utilizar
    private Intent intent;
    private TextView materia_materiales;
    private TextView fecha_materiales;
    private TextView descripcion_materiales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_material);

        intent = getIntent();

        materia_materiales = (TextView) findViewById(R.id.materia_materiales);
        fecha_materiales = (TextView) findViewById(R.id.fecha_materiales);
        descripcion_materiales = (TextView) findViewById(R.id.descripcion_materiales);

        rellenarVista();
    }

    private void rellenarVista()
    {
        materia_materiales.setText(intent.getStringExtra("materia"));
        fecha_materiales.setText(intent.getStringExtra("fecha"));
        descripcion_materiales.setText(intent.getStringExtra("descripcion"));
    }
}
