package com.tenis.manager.tenis.entities;



public class CumpleanieroDTO {

    private String nombre;
    private String celular;
    private String tipo; // "Alumno" o "Profesor"

    public CumpleanieroDTO(String nombre, String celular, String tipo) {
        this.nombre = nombre;
        this.celular = celular;
        this.tipo = tipo;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
