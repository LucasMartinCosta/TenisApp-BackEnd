package com.tenis.manager.tenis.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tenis.manager.tenis.entities.CumpleanieroDTO;
import com.tenis.manager.tenis.entities.Profesor;

public interface ProfeRepo extends JpaRepository<Profesor, Long>{

    @Query("SELECT new com.tenis.manager.tenis.entities.CumpleanieroDTO(p.nombre, p.celular, 'Profesor') " +
           "FROM Profesor p " +
           "WHERE FUNCTION('day', p.fechaNacimiento) = :dia " +
           "AND FUNCTION('month', p.fechaNacimiento) = :mes")
    List<CumpleanieroDTO> buscarCumpleanieros(@Param("dia") int dia, @Param("mes") int mes);

}
