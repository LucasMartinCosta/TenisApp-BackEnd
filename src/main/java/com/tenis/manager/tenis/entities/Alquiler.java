package com.tenis.manager.tenis.entities;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ALQUILER")
public class Alquiler extends Reserva{


    private Double valor;

    public Alquiler() {
    }

    public Alquiler(Long id, Cancha cancha, LocalDate fecha, LocalTime inicio, LocalTime finalizacion, String nombre,
            Double valor) {
        super(id, cancha, fecha, inicio, finalizacion, nombre);
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    } 

    

    
}
