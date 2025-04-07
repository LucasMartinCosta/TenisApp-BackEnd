package com.tenis.manager.tenis.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name="precios")
public class Precio {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id; 

    private String nombre; 

    private Long precio;

    

    public Precio(){
        
    }

    public Precio(Long id, String nombre, Long precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
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

    public Long getPrecio() {
        return precio;
    }

    public void setPrecio(Long precio) {
        this.precio = precio;
    } 

    
}
