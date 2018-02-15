package com.usach.sebastianvallejos.scap_apoderados.Models;

/**
 * Created by sebastianvallejos on 15-02-18.
 */

public class Profesores {

    //Atributos de la clase
    private String nombre;
    private String apellido;
    private String correo;
    private String edad;

    //Constructor requerido por FireBase
    public Profesores(){}

    //Constructor con asignacion de atributos incluidos
    public Profesores(String name, String last_name, String email, String age)
    {
        this.nombre = name;
        this.apellido = last_name;
        this.correo = email;
        this.edad = age;

    }

    //Getters para obtener los datos de la clase
    public String getNombre() { return this.nombre; }
    public String getApellido() { return this.apellido; }
    public String getCorreo() { return this.correo; }
    public String getEdad() { return this.edad; }

}
