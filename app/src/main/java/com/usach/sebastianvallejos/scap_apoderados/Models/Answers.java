package com.usach.sebastianvallejos.scap_apoderados.Models;

public class Answers {
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String respuesta_tiempo;
    private String respuesta_dificultad;
    private String respuesta_motivacion;
    private String detalle;
    private String idApoderado;

    public Answers(){}

    public Answers(String name, String father_last_name, String mother_last_name, String time, String difficulty, String third, String detail, String identifier){
        this.nombre = name;
        this.apellidoMaterno = mother_last_name;
        this.apellidoPaterno = father_last_name;
        this.detalle = detail;
        this.respuesta_tiempo = time;
        this.respuesta_dificultad = difficulty;
        this.respuesta_motivacion = third;
        this.idApoderado = identifier;
    }

    //Getters

    public String getIdApoderado() { return idApoderado; }
    public String getNombre() { return nombre; }
    public String getApellidoPaterno() { return apellidoPaterno; }
    public String getApellidoMaterno() { return apellidoMaterno; }
    public String getDetalle() { return detalle; }
    public String getRespuesta_tiempo() { return respuesta_tiempo; }
    public String getRespuesta_dificultad() { return respuesta_dificultad; }
    public String getRespuesta_motivacion() { return respuesta_motivacion; }

    //Setters

    public void setIdApoderado(String idApoderado) { this.idApoderado = idApoderado; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellidoPaterno(String apellidoPaterno) { this.apellidoPaterno = apellidoPaterno; }
    public void setApellidoMaterno(String apellidoMaterno) { this.apellidoMaterno = apellidoMaterno; }
    public void setDetalle(String detalle) { this.detalle = detalle; }
    public void setRespuesta_tiempo(String respuesta) { this.respuesta_tiempo = respuesta; }
    public void setRespuesta_dificultad(String respuesta) { this.respuesta_dificultad = respuesta; }
    public void setRespuesta_motivacion(String respuesta) { this.respuesta_motivacion = respuesta; }
}
