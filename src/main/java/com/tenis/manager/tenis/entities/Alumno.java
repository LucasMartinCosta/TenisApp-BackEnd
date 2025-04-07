package com.tenis.manager.tenis.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="alumnos")
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; 

     @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate fechaNacimiento; 

    private String celular;

    private Boolean mesActualPago;

    private String vecesXsemana; // uno - dos - tres


    public Alumno() {
    }

    public Alumno(Long id, String nombre, LocalDate fechaNacimiento, String celular, Boolean mesActualPago, String vecesXsemana) {
        this.id = id;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.celular = celular;
        this.mesActualPago = mesActualPago;
        this.vecesXsemana = vecesXsemana;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Boolean getMesActualPago() {
        return mesActualPago;
    }

    public void setMesActualPago(Boolean mesActualPago) {
        this.mesActualPago = mesActualPago;
    }

    public String getVecesxSemana() {
        return vecesXsemana;
    }

    public void setvecesXsemana(String vecesXsemana) {
        this.vecesXsemana = vecesXsemana;
    }

    

    

}
