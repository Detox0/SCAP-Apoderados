package com.usach.sebastianvallejos.scap_apoderados.Models;

import android.content.Intent;

/**
 * Created by sebastianvallejos on 12-02-18.
 */

public class Apoderados {

    //Atributos de la clase
    private String id;
    private Integer edad;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;

    //Constructor requerido por FireBase
    public Apoderados(){}

    //Constructor con asignacion de atributos incluidos
    public Apoderados(String name, String last_namep, String last_namem, String email, Integer age)
    {
        this.nombre = name;
        this.apellidoPaterno = last_namep;
        this.apellidoMaterno = last_namem;
        this.correo = email;
        this.edad = age;
    }

    //Getters para obtener los datos de la clase
    public String getId() { return this.id; }
    public String getCorreo() { return this.correo; }
    public String getNombre() { return this.nombre; }
    public String getApellidoPaterno() { return this.apellidoPaterno; }
    public String getApellidoMaterno() { return this.apellidoMaterno; }

    //Setters
    public void setId(String aidi){ this.id = aidi; }

}
