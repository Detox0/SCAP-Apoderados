package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.usach.sebastianvallejos.scap_apoderados.Models.Ses;
import com.usach.sebastianvallejos.scap_apoderados.R;

import org.w3c.dom.Text;

public class DetailSESActivity extends AppCompatActivity {

    //Variables a utilizar
    private FirebaseDatabase mdDataBase = FirebaseDatabase.getInstance();
    private Ses ses = new Ses();
    private Intent intent;
    private TextView profesor_ses;
    private TextView materia_ses;
    private TextView fecha_ses;
    private TextView descripcion_ses;
    private Button positivo;
    private Button negativo;
    private String colegio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ses);

        intent = getIntent();

        profesor_ses = (TextView) findViewById(R.id.titulo_ses);
        materia_ses = (TextView) findViewById(R.id.materia_ses);
        fecha_ses = (TextView) findViewById(R.id.fecha_ses);
        descripcion_ses = (TextView) findViewById(R.id.descripcion_ses);
        positivo = (Button) findViewById(R.id.ses_boton_positivo);
        negativo = (Button) findViewById(R.id.ses_boton_negativo);

        crearActividad();
    }

    private void crearActividad()
    {
        ses.setDescripcion(intent.getStringExtra("descripcion"));
        ses.setProfesor(intent.getStringExtra("profesor"));
        ses.setFecha(intent.getStringExtra("fecha"));
        ses.setDescripcion(intent.getStringExtra("descripcion"));
        ses.setId(intent.getStringExtra("id"));
        colegio = intent.getStringExtra("colegio");

        setearVista();
        setearBotonPositivo();
        setearBotonNegativo();
    }

    private void setearVista()
    {
        profesor_ses.setText(ses.getProfesor());
        materia_ses.setText(ses.getMateria());
        fecha_ses.setText(ses.getFecha());
        descripcion_ses.setText(ses.getDescripcion());
    }

    private void setearBotonPositivo()
    {
        positivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DatabaseReference positivoRef = mdDataBase.getReference(colegio);

                positivoRef.child("ses/"+ses.getId()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Ses actividad = dataSnapshot.getValue(Ses.class);

                        //Obtener las positivas y aumentar en 1 su valor
                        Integer positivas = Integer.valueOf(actividad.getPositivas());
                        positivas ++;
                        positivoRef.child("ses/"+ses.getId()).child("positivas").setValue(positivas.toString());

                        //Volvemos al menu anterior
                        finish();

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
        });
    }

    private void setearBotonNegativo()
    {

        negativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference negativoRef = mdDataBase.getReference(colegio);

                negativoRef.child("ses/"+ses.getId()).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                        Ses actividad = dataSnapshot.getValue(Ses.class);

                        //Obtener las positivas y aumentar en 1 su valor
                        Integer negativas = Integer.valueOf(actividad.getNegativas());
                        negativas ++;
                        negativoRef.child("ses/"+ses.getId()).child("negativas").setValue(negativas.toString());

                        //Volvemos al menu anterior
                        finish();

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
        });

    }
}
