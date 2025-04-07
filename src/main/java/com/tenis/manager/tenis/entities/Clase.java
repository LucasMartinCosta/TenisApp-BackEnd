package com.tenis.manager.tenis.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("CLASE")
public class Clase extends Reserva{

    
    @ManyToMany
    @JoinTable(
        name = "clase_alumno",
        joinColumns = @JoinColumn(name = "clase_id"),
        inverseJoinColumns = @JoinColumn(name = "alumno_id")
    )
    private List<Alumno> alumnos;

    @ManyToOne 
    Profesor profe;

    public Clase() {
    }

    public Clase(Long id, Cancha cancha, LocalDate fecha, LocalTime inicio, LocalTime finalizacion, String nombre,
            List<Alumno> alumnos, Profesor profe) {
        super(id, cancha, fecha, inicio, finalizacion, nombre);
        this.alumnos = alumnos;
        this.profe = profe;
    }

    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    public Profesor getProfe() {
        return profe;
    }

    public void setProfe(Profesor profe) {
        this.profe = profe;
    } 

    

}
