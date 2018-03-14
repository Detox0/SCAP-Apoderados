package com.usach.sebastianvallejos.scap_apoderados.Models;

/**
 * Created by sebastianvallejos on 05-03-18.
 */

public class Ses {
    private String id;
    private String profesor;
    private String descripcion;
    private String fecha;
    private String seccion;
    private String positivas;
    private String negativas;
    private String materia;

    public Ses(){}

    public Ses(String aidi, String teacher ,String desc, String date, String section, String positive, String negative, String subject)
    {
        this.id = aidi;
        this.profesor = teacher;
        this.descripcion = desc;
        this.seccion = section;
        this.fecha = date;
        this.positivas = positive;
        this.negativas = negative;
        this.materia = subject;
    }

    //Getters
    public String getId(){ return this.id; }
    public String getFecha() {return fecha;}
    public String getSeccion() {return seccion;}
    public String getNegativas() {return negativas;}
    public String getPositivas() {return positivas;}
    public String getProfesor(){ return this.profesor; }
    public String getDescripcion() {return descripcion;}
    public String getMateria() {return materia;}

    //Setters
    public void setId(String id) {this.id = id;}
    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}
    public void setFecha(String fecha) {this.fecha = fecha;}
    public void setProfesor(String profesor) {this.profesor = profesor;}
    public void setSeccion(String seccion) {this.seccion = seccion;}
    public void setPositivas(String positivas) {this.positivas = positivas;}
    public void setNegativas(String negativas) {this.negativas = negativas;}
    public void setMateria(String materia) {this.materia = materia;}
}
