package com.usach.sebastianvallejos.scap_apoderados.Models;

/**
 * Created by sebastianvallejos on 15-02-18.
 */

public class Alumnos {

    //Atributos de la clase
    private String id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String seccion;
    private String colegio;

    //Constructor requerido por FireBase
    public Alumnos(){}

    //Constructor con asignacion de atributos incluidos
    public Alumnos(String aidi, String name, String last_nameP, String last_nameM, String section,String school)
    {

        this.id = aidi;
        this.nombre = name;
        this.apellidoPaterno = last_nameP;
        this.apellidoMaterno = last_nameM;
        this.seccion = section;
        this.colegio = school;

    }

    //Getters para obtener los datos de la clase
    public String getId(){ return this.id; }
    public String getNombre() { return this.nombre; }
    public String getApellidoPaterno() { return this.apellidoPaterno; }
    public String getApellidoMaterno() { return this.apellidoMaterno; }
    public String getSeccion() { return this.seccion; }
    public String getColegio() { return this.colegio; }

    //Setters para poder guardar la informacion obtenida
    public void setId(String aidi){ this.id=aidi; }
    public void setNombre(String name){ this.nombre=name; }
    public void setApellidoPaterno(String apaterno){ this.apellidoPaterno=apaterno; }
    public void setApellidoMaterno(String amaterno){ this.apellidoMaterno=amaterno; }
    public void setSeccion(String section){ this.seccion=section; }
    public void setColegio(String school){ this.colegio=school; }

}
