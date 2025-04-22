package com.tenis.manager.tenis.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.tenis.manager.tenis.entities.CumpleanieroDTO;
import com.tenis.manager.tenis.repositories.AlumnoRepo;
import com.tenis.manager.tenis.repositories.ProfeRepo;

@Service
public class CumpleService {

    private final AlumnoRepo alumnoRepo; 
    private final ProfeRepo profeRepo; 

    public CumpleService (AlumnoRepo alumnoRepo, ProfeRepo profeRepo) {
        this.alumnoRepo=alumnoRepo;
        this.profeRepo=profeRepo;
        
    }

     public List<CumpleanieroDTO> obtenerCumpleanierosHoy() {
        LocalDate hoy = LocalDate.now();
        int dia = hoy.getDayOfMonth();
        int mes = hoy.getMonthValue();

        List<CumpleanieroDTO> cumpleanieros = new ArrayList<>();

        // Buscar alumnos
        cumpleanieros.addAll(alumnoRepo.buscarCumpleanieros(dia, mes));

        // Buscar profesores
        cumpleanieros.addAll(profeRepo.buscarCumpleanieros(dia, mes));

        return cumpleanieros;
    }
    

}
