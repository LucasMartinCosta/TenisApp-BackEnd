package com.tenis.manager.tenis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tenis.manager.tenis.entities.Alumno;
import com.tenis.manager.tenis.entities.CumpleanieroDTO;

public interface AlumnoRepo extends JpaRepository<Alumno, Long>{

    // Buscar por nombre (exacto)
    List<Alumno> findByNombre(String nombre);

    // Buscar por nombre que contiene algo (como búsqueda parcial)
    List<Alumno> findByNombreContainingIgnoreCase(String nombre);

    // Filtrar por cantidad de veces que va por semana
    List<Alumno> findByVecesXsemana(String veces);

    // Filtrar alumnos que NO pagaron la cuota
    List<Alumno> findBymesActualPagoFalse();

    @Query("SELECT new com.tenis.manager.tenis.entities.CumpleanieroDTO(a.nombre, a.celular, 'Alumno') " +
           "FROM Alumno a " +
           "WHERE FUNCTION('day', a.fechaNacimiento) = :dia " +
           "AND FUNCTION('month', a.fechaNacimiento) = :mes")
    List<CumpleanieroDTO> buscarCumpleanieros(@Param("dia") int dia, @Param("mes") int mes);

}
