package com.usach.sebastianvallejos.scap_apoderados.Models;

/**
 * Created by sebastianvallejos on 15-02-18.
 */

public class Actividad {

    //Atributos de la clase
    private String seccion;
    private String materia;
    private String profesor;
    private String tipo;
    private String descripcion;

    //Constructor requerido por FireBase
    public Actividad(){}

    //Constructor con asignacion de atributos incluidos
    public Actividad(String section, String subject, String proffesor, String type, String description)
    {
        this.seccion = section;
        this.materia = subject;
        this.profesor = proffesor;
        this.tipo = type;
        this.descripcion = description;

    }

    //Getters para obtener los datos de la clase
    public String getSeccion() { return this.seccion; }
    public String getMateria() { return this.materia; }
    public String getProfesor() { return this.profesor; }
    public String getTipo() { return this.tipo; }
    public String getDescripcion() { return this.descripcion; }

}
