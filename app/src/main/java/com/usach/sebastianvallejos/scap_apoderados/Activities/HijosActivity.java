package com.usach.sebastianvallejos.scap_apoderados.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.usach.sebastianvallejos.scap_apoderados.Models.Alumnos;
import com.usach.sebastianvallejos.scap_apoderados.Models.Apoderados;
import com.usach.sebastianvallejos.scap_apoderados.R;

import java.util.ArrayList;
import java.util.List;

public class HijosActivity extends AppCompatActivity {

    //Variables necesarias
    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Apoderados parent = new Apoderados();
    private List<String> listaVacia = new ArrayList<String>();
    private List<String> nombreAlumnos = new ArrayList<String>();
    private List<Alumnos> alumnos = new ArrayList<Alumnos>();
    private List<String> colegios = new ArrayList<String>();
    private ArrayAdapter alumnosAdapter;
    private Spinner spinnerColegios;
    private Spinner spinnerAlumnos;
    private String colegio = "";
    private String correo;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hijos);

        spinnerColegios = (Spinner) findViewById(R.id.fidget_colegios);
        spinnerAlumnos = (Spinner) findViewById(R.id.fidget_alumnos);
        intent = getIntent();
        correo = intent.getStringExtra("correo");

        //Obtenemos los colegios dentro de la BD, rellenamos el spinner y lo mostramos al usuario
        obtenerColegios();

        //Agregamos un listener al boton para poder continuar a la siguiente vista
        setearListener();
    }

    //Funcion que se encarga de retornar los colegios dentro de la BD
    public void obtenerColegios()
    {

        DatabaseReference colegiosRef = mDataBase.getReference("colegios");

        colegiosRef.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    colegio = data.getKey().toString();
                    colegios.add(colegio);
                }

                //Le entregamos los datos al spinner
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HijosActivity.this, android.R.layout.simple_spinner_item, colegios);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerColegios.setAdapter(arrayAdapter);

                spinnerColegios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if(parent != null)
                        {
                            if(alumnosAdapter != null)
                            {
                                alumnosAdapter.clear();
                            }
                            nombreAlumnos.clear();
                            colegio = spinnerColegios.getSelectedItem().toString();
                            obtenerApoderados();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        obtenerApoderados();
    }

    //Funcion que recupera a los apoderados de la BD del colegio
    public void obtenerApoderados()
    {
        DatabaseReference apoderadosRef = mDataBase.getReference(colegio);

        apoderadosRef.child("apoderados").orderByChild("correo").equalTo(correo).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                parent = dataSnapshot.getValue(Apoderados.class);

                parent.setId(dataSnapshot.getKey().toString());
                obtenerHijos();
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

    //Funcion que recupera los hijos de un apoderado
    //Entrada: correo del apoderado
    public void obtenerHijos()
    {
        final DatabaseReference apoderadosRef = mDataBase.getReference(spinnerColegios.getSelectedItem().toString());

        apoderadosRef.child("apoderados/"+parent.getId().toString()+"/hijos").orderByChild("nombre").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot data: dataSnapshot.getChildren())
                {
                    Alumnos alumno = data.getValue(Alumnos.class);
                    alumnos.add(alumno);
                    nombreAlumnos.add(alumno.getNombre());
                }

                //Le entregamos los datos al spinner
                alumnosAdapter = new ArrayAdapter<String>(HijosActivity.this, android.R.layout.simple_spinner_item, nombreAlumnos);
                alumnosAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                alumnosAdapter.notifyDataSetChanged();
                spinnerAlumnos.setAdapter(alumnosAdapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //Listener para el boton "hecho"
    public void setearListener()
    {
        Button boton = (Button) findViewById(R.id.boton_hecho);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String alumne = spinnerAlumnos.getSelectedItem().toString();
                Alumnos student = new Alumnos();

                //Verificamos que se haya seleccionado un alumno
                if(alumne != null)
                {
                    //Buscamos al alumno dentro de nuestro arreglo de alumnos
                    for (int i=0; i<alumnos.size(); i++)
                    {
                        if(alumne == alumnos.get(i).getNombre())
                        {
                            student = alumnos.get(i);
                            i = alumnos.size();
                        }
                    }

                    //Se guardan datos para el siguiente intent
                    intent = new Intent(HijosActivity.this,MainMenuActivity.class);

                    //intent.putExtra("idPadre",parent.getId());
                    intent.putExtra("id",student.getId());
                    intent.putExtra("nombre",student.getNombre());
                    intent.putExtra("seccion",student.getSeccion());
                    intent.putExtra("colegio",colegio);
                    intent.putExtra("apellidoPaterno",student.getApellidoPaterno());
                    intent.putExtra("apellidoMaterno",student.getApellidoMaterno());

                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(HijosActivity.this,"No se ha seleccionado un alumno, porfavor seleccione uno.",Toast.LENGTH_SHORT);
                }
            }
        });
    }
}
