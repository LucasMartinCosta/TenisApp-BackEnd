package com.tenis.manager.tenis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tenis.manager.tenis.entities.Alumno;

public interface AlumnoRepo extends JpaRepository<Alumno, Long>{

    // Buscar por nombre (exacto)
    List<Alumno> findByNombre(String nombre);

    // Buscar por nombre que contiene algo (como búsqueda parcial)
    List<Alumno> findByNombreContainingIgnoreCase(String nombre);

    // Filtrar por cantidad de veces que va por semana
    List<Alumno> findByVecesXsemana(String veces);

    // Filtrar alumnos que NO pagaron la cuota
    List<Alumno> findBymesActualPagoFalse();

}
