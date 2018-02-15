package com.usach.sebastianvallejos.scap_apoderados.Models;

/**
 * Created by sebastianvallejos on 12-02-18.
 */

public class Apoderados {

    //Atributos de la clase
    private String nombre;
    private String apellido;
    private String correo;

    //Constructor requerido por FireBase
    public Apoderados(){}

    //Constructor con asignacion de atributos incluidos
    public Apoderados(String name, String last_name, String email, String age)
    {
        this.nombre = name;
        this.apellido = last_name;
        this.correo = email;

    }

    //Getters para obtener los datos de la clase
    public String getNombre() { return this.nombre; }
    public String getApellido() { return this.apellido; }
    public String getCorreo() { return this.correo; }

}
