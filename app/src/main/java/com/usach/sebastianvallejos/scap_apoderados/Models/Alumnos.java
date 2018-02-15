package com.usach.sebastianvallejos.scap_apoderados.Models;

/**
 * Created by sebastianvallejos on 15-02-18.
 */

public class Alumnos {

    //Atributos de la clase
    private String nombre;
    private String apellido;
    private String seccion;

    //Constructor requerido por FireBase
    public Alumnos(){}

    //Constructor con asignacion de atributos incluidos
    public Alumnos(String name, String last_name, String email, String age)
    {
        this.nombre = name;
        this.apellido = last_name;
        this.seccion = email;

    }

    //Getters para obtener los datos de la clase
    public String getNombre() { return this.nombre; }
    public String getApellido() { return this.apellido; }
    public String getCorreo() { return this.seccion; }
}
