package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usach.sebastianvallejos.scap_apoderados.Models.Answers;
import com.usach.sebastianvallejos.scap_apoderados.R;

public class FinalSESActivity extends AppCompatActivity {

    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Intent intent;
    private Button enviar;
    private EditText comentario;
    private String apellido_paterno;
    private String apellido_materno;
    private String idPadre;
    private String respuesta;
    private String idSES;
    private String nombre_alumno;
    private String colegio;
    private RadioGroup first_questions;
    private RadioGroup second_questions;
    private RadioGroup third_questions;
    private RadioButton facil;
    private RadioButton normal;
    private RadioButton dificil;
    private RadioButton menos;
    private RadioButton entre;
    private RadioButton mas;
    private RadioButton entusiasmado;
    private RadioButton desinteresado;
    private RadioButton aburrido;
    private String seccion;
    private String idAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_ses);
        intent = getIntent();
        get_activity_data();
        obtenerData();
        setearListener();
    }

    private void get_activity_data(){
        enviar = (Button) findViewById(R.id.send_button);
        comentario = (EditText) findViewById(R.id.comentarios_finales);
        first_questions = (RadioGroup) findViewById(R.id.first_question);
        second_questions = (RadioGroup) findViewById(R.id.second_question);
        third_questions = (RadioGroup) findViewById(R.id.third_question);
        facil = (RadioButton) findViewById(R.id.facil);
        normal = (RadioButton) findViewById(R.id.normal);
        dificil = (RadioButton) findViewById(R.id.dificil);
        menos = (RadioButton) findViewById(R.id.menos5);
        entre = (RadioButton) findViewById(R.id.entre5);
        mas = (RadioButton) findViewById(R.id.mas10);
        entusiasmado = (RadioButton) findViewById(R.id.q1);
        desinteresado = (RadioButton) findViewById(R.id.q2);
        aburrido = (RadioButton) findViewById(R.id.q3);
    }

    private void obtenerData() {
        apellido_paterno = intent.getStringExtra("apellido_paterno_alumno");
        apellido_materno = intent.getStringExtra("apellido_materno_alumno");
        idPadre = intent.getStringExtra("idPadre");
        //respuesta = verificar_respuesta(intent.getStringExtra("respuesta"));
        idSES = intent.getStringExtra("idSES");
        nombre_alumno = intent.getStringExtra("nombre_alumno");
        colegio = intent.getStringExtra("colegio");
        this.idAlumno = intent.getStringExtra("idAlumno");
        this.seccion = intent.getStringExtra("seccion");
    }

    private void setearListener(){
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(
                        first_questions.getCheckedRadioButtonId() != -1 &&
                        second_questions.getCheckedRadioButtonId() != -1 &&
                        third_questions.getCheckedRadioButtonId() != -1)
                {
                    Answers respuestas = new Answers();
                    respuestas.setNombre(nombre_alumno);
                    respuestas.setApellidoPaterno(apellido_paterno);
                    respuestas.setApellidoMaterno(apellido_materno);
                    respuestas.setRespuesta_tiempo(obtener_respuesta_tiempo());
                    respuestas.setRespuesta_dificultad(obtener_respuesta_dificultad());
                    respuestas.setRespuesta_motivacion(obtener_respuesta_3());
                    respuestas.setDetalle(String.valueOf(comentario.getText()));
                    respuestas.setIdApoderado(idPadre);
                    DatabaseReference guardarRef = mDataBase.getReference(colegio);
                    DatabaseReference respRef = guardarRef.child("ses").child(idSES).child("respuestas").push();
                    respRef.setValue(respuestas);
                    Intent intent = new Intent(FinalSESActivity.this, MainMenuActivity.class);
                    intent.putExtra("idPadre",idPadre);
                    intent.putExtra("id", idAlumno);
                    intent.putExtra("nombre",nombre_alumno );
                    intent.putExtra("colegio", colegio);
                    intent.putExtra("seccion", seccion);
                    intent.putExtra("apellidoPaterno", apellido_paterno);
                    intent.putExtra("apellidoMaterno", apellido_materno);
                    Toast.makeText(getApplicationContext(), "Actividad enviada satisfactoriamente",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), R.string.alert_finalSES, Toast.LENGTH_LONG);
                }
            }
        });
    }

    private String obtener_respuesta_tiempo() {
        String respuesta;
        if (menos.isChecked()) {
            respuesta = "menos";
        }
        else if (entre.isChecked()) {
            respuesta = "entre";
        }
        else {
            respuesta = "mas";
        }
        aumentar_tiempo(respuesta);
        return respuesta;
    }

    private void aumentar_tiempo(final String respuesta){
        final DatabaseReference tiempoRef = mDataBase.getReference(colegio);

        tiempoRef.child("ses").child(idSES).child(respuesta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long tiempo = (long) dataSnapshot.getValue();
                tiempoRef.child("ses").child(idSES).child(respuesta).setValue(tiempo+1);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FinalSESActivity.this, R.string.DBerror, Toast.LENGTH_LONG);
            }
        });
    }


    private String obtener_respuesta_dificultad() {
        String respuesta;
        if (facil.isChecked()) {
            respuesta = "faciles";
        }
        else if (normal.isChecked()) {
            respuesta = "normales";
        }
        else {
            respuesta = "dificiles";
        }
        aumentar_dificultad(respuesta);
        return respuesta;
    }

    private void aumentar_dificultad(final String respuesta){
        final DatabaseReference tiempoRef = mDataBase.getReference(colegio);

        tiempoRef.child("ses").child(idSES).child(respuesta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long tiempo = (long) dataSnapshot.getValue();
                tiempoRef.child("ses").child(idSES).child(respuesta).setValue(tiempo+1);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FinalSESActivity.this, R.string.DBerror, Toast.LENGTH_LONG);
            }
        });
    }


    private String obtener_respuesta_3() {
        String respuesta;
        if (entusiasmado.isChecked()) {
            respuesta = "entusiasmado";
        }
        else if (desinteresado.isChecked()) {
            respuesta = "desinteresado";
        }
        else {
            respuesta = "aburrido";
        }
        aumentar_pregunta_3(respuesta);
        return respuesta;
    }

    private void aumentar_pregunta_3(final String respuesta){
        final DatabaseReference tiempoRef = mDataBase.getReference(colegio);

        tiempoRef.child("ses").child(idSES).child(respuesta).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long tiempo = (long) dataSnapshot.getValue();
                tiempoRef.child("ses").child(idSES).child(respuesta).setValue(tiempo+1);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(FinalSESActivity.this, R.string.DBerror, Toast.LENGTH_LONG);
            }
        });
    }


    private void aumentar_positivas()
    {
        final DatabaseReference positivoRef = mDataBase.getReference(colegio);

        positivoRef.child("ses").child(idSES).child("positivas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long positivas = (long) dataSnapshot.getValue();
                positivoRef.child("ses").child(idSES).child("positivas").setValue(positivas+1);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void aumentar_negativas()
    {
        final DatabaseReference negativoRef = mDataBase.getReference(colegio);

        negativoRef.child("ses").child(idSES).child("negativas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                long negativas = (long) dataSnapshot.getValue();
                negativoRef.child("ses").child(idSES).child("negativas").setValue(negativas+1);
                finish();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private String verificar_respuesta(String respuesta){
        if (respuesta.equals("positiva")){ return "Positiva"; }
        else { return "Negativa"; }
    }

}
