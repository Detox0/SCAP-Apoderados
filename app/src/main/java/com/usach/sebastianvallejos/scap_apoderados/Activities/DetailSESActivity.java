package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private String colegio;
    private String nombre_alumno;
    private String apellido_paterno_alumno;
    private String apellido_materno_alumno;
    private String idPadre;
    private String idAlumno;
    private String seccion;

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

        obtenerData();
    }

    private void obtenerData()
    {
        ses.setDescripcion(intent.getStringExtra("descripcion"));
        ses.setProfesor(intent.getStringExtra("profesor"));
        ses.setFecha(intent.getStringExtra("fecha"));
        ses.setDescripcion(intent.getStringExtra("descripcion"));
        ses.setId(intent.getStringExtra("id"));
        ses.setMateria(intent.getStringExtra("materia"));
        this.colegio = intent.getStringExtra("colegio");
        this.nombre_alumno = intent.getStringExtra("nombre");
        this.apellido_paterno_alumno = intent.getStringExtra("apellido_paterno_alumno");
        this.apellido_materno_alumno = intent.getStringExtra("apellido_materno_alumno");
        this.idPadre = intent.getStringExtra("idPadre");
        this.idAlumno = intent.getStringExtra("idAlumno");
        this.seccion = intent.getStringExtra("seccion");

        setearVista();
        setearBotonPositivo();
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

                Intent intent = new Intent(DetailSESActivity.this, FinalSESActivity.class);
                try{
                    intent.putExtra("idPadre", idPadre);
                    intent.putExtra("idSES", ses.getId());
                    intent.putExtra("nombre_alumno", nombre_alumno);
                    intent.putExtra("apellido_paterno_alumno", apellido_paterno_alumno);
                    intent.putExtra("apellido_materno_alumno", apellido_materno_alumno);
                    intent.putExtra("colegio", colegio);
                    intent.putExtra("idAlumno", idAlumno);
                    intent.putExtra("seccion", seccion);
                    startActivity(intent);
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),""+e.toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
