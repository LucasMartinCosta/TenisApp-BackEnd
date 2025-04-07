package com.tenis.manager.tenis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenis.manager.tenis.entities.Alumno;
import com.tenis.manager.tenis.repositories.AlumnoRepo;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepo alumnoRepo; 

     public List <Alumno> findAll (){
        return (List<Alumno>)  this.alumnoRepo.findAll();
    }

    public Optional<Alumno> findById (Long id){
        return this.alumnoRepo.findById(id);
    }

    public Alumno saveAlumno (Alumno dato){
        return this.alumnoRepo.save(dato);
    }

    public Alumno updateAlumno (Long id, Alumno dato){

        Alumno encontrado = this.alumnoRepo.findById(id).get();

        encontrado.setFechaNacimiento(dato.getFechaNacimiento());
        encontrado.setCelular(dato.getCelular());
        encontrado.setMesActualPago(dato.getMesActualPago());
        encontrado.setvecesXsemana(dato.getVecesxSemana());
        encontrado.setNombre(dato.getNombre());

        return this.alumnoRepo.save(encontrado);

    }

    public Alumno deleteAlumno (Long id){

        return this.alumnoRepo.findById(id)
        .map(alumno -> {
            this.alumnoRepo.delete(alumno);
            return alumno;
        })
        .orElseThrow(()-> new RuntimeException("alumno con ID " + id + "no se encuentra en la BDD"));

    }

}
