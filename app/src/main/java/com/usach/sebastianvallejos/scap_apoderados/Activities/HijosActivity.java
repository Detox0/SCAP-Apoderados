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
import com.usach.sebastianvallejos.scap_apoderados.Models.Alumnos;
import com.usach.sebastianvallejos.scap_apoderados.Models.Apoderados;
import com.usach.sebastianvallejos.scap_apoderados.R;

import java.util.ArrayList;
import java.util.List;

public class HijosActivity extends AppCompatActivity {

    //Variables necesarias
    private FirebaseDatabase mDataBase = FirebaseDatabase.getInstance();
    private Apoderados apoderado;
    private List<String> nombreAlumnos = new ArrayList<String>();
    private List<Alumnos> alumnos = new ArrayList<Alumnos>();
    private List<String> colegios = new ArrayList<String>();
    private Spinner spinnerColegios;
    private Spinner spinnerAlumnos;
    private String colegio;
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

        //Obtenemos los colegios dentro de la BD y la mostramos al usuario
        obtenerColegios();
        //rellenarSpinnerColegios(colegios);

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.support_simple_spinner_dropdown_item, colegios);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerColegios.setAdapter(spinnerArrayAdapter);

        spinnerColegios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("PRUEBA","Entra a onItemSelectedListener.");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.i("PRUEBA","No entra a onItemSelectedListener.");
            }
        });


        //Agregamos un listener al boton para poder continuar a la siguiente vista
        //setearListener();
    }

    //Funcion que se encarga de retornar los colegios dentro de la BD
    public void obtenerColegios()
    {

        DatabaseReference colegiosRef = mDataBase.getReference("colegios");

        colegiosRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                colegios.add(dataSnapshot.getKey().toString());

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

    //Funcion encargada de rellenar el Spinner de la vista con las materias recuperadas de la base de datos
    //Entrada: Una lista con las materias obtenidas de la BD
    public void rellenarSpinnerColegios(List arreglo)
    {

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,arreglo);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerColegios.setAdapter(spinnerArrayAdapter);

/*        spinnerColegios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                Log.i("PRUEBA","Entro al item selected");

                colegio = parentView.getItemAtPosition(position).toString();

                //colegio = spinnerColegios.getSelectedItem().toString();
                Log.i("PRUEBA","Se ha encontrado: "+colegio);
                obtenerApoderados(colegio);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.i("PRUEBA","MAMGUI");
            }

        });*/
    }

    //Funcion que se encarga de crear el otro Spinner una vez que se ha seleccionado un colegio
    public void accionSpinner()
    {

        Log.i("CO","ENTRO a accionSpinner");


    }

    //Funcion que recupera a los apoderados de la BD del colegio
    public void obtenerApoderados(String colegio)
    {

        Log.i("CO","ENTRO a apoderados");

        DatabaseReference apoderadosRef = mDataBase.getReference(colegio);

        apoderadosRef.child("apoderados").orderByChild("correo").equalTo(correo).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                apoderado = dataSnapshot.getValue(Apoderados.class);

                apoderado.setId(dataSnapshot.getKey().toString());

                obtenerHijos(apoderado);
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
    public void obtenerHijos(Apoderados parent)
    {

        Log.i("CO","ENTRO a alumnos");

        final DatabaseReference apoderadosRef = mDataBase.getReference(colegio);

        apoderadosRef.child("apoderados/"+parent.getId().toString()+"/hijos").orderByChild("nombre").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Alumnos alumno = dataSnapshot.getValue(Alumnos.class);
                alumno.setId(dataSnapshot.getKey());

                alumnos.add(alumno);
                nombreAlumnos.add(alumno.getNombre());

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

        rellenarSpinnerAlumnos(nombreAlumnos);
    }

    //Funcion encargada de rellenar el Spinner de la vista con las materias recuperadas de la base de datos
    //Entrada: Una lista con las materias obtenidas de la BD
    public void rellenarSpinnerAlumnos(List arreglo)
    {

        Log.i("CO","ENTRO a rellenar Alumnos");

        Spinner spinner = (Spinner) findViewById(R.id.fidget_alumnos);

        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.support_simple_spinner_dropdown_item,arreglo);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerAlumnos.setAdapter(spinnerArrayAdapter);
    }

    //Listener para el boton "hecho"
    public void setearListener()
    {
        Button boton = (Button) findViewById(R.id.boton_hecho);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PREGUNTAR ANTES
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

                    Log.i("CO","Encontro: "+student.getNombre());

                    //Se guardan datos para el siguiente intent
                    intent = new Intent(HijosActivity.this,MainMenuActivity.class);

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
