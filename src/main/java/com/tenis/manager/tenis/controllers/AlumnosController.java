package com.tenis.manager.tenis.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenis.manager.tenis.entities.Alumno;
import com.tenis.manager.tenis.services.AlumnoService;


import org.springframework.transaction.annotation.Transactional;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnosController {


     @Autowired
    private AlumnoService service;


    @GetMapping
    @Transactional (readOnly = true)
    public ResponseEntity <List <Alumno>> findAllAlumnos (){
        List <Alumno> alumnos = this.service.findAll();
        if(alumnos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alumnos);
    }


    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Alumno> getAlumnoByid (@PathVariable Long id){
        return this.service.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alumno> saveAlumno (@RequestBody Alumno dato){
        Alumno nuevo = this.service.saveAlumno(dato);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); 
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateAlumno (@PathVariable Long id, @RequestBody Alumno dato){
        Optional <Alumno> alumnoOptional = this.service.findById(id); 

        if(alumnoOptional.isPresent()){
            Alumno alumno = alumnoOptional.get();
            alumno.setNombre(dato.getNombre());
            alumno.setCelular(dato.getCelular());
            alumno.setFechaNacimiento(dato.getFechaNacimiento());
            alumno.setMesActualPago(dato.getMesActualPago());
            alumno.setvecesXsemana(dato.getVecesxSemana());

            Alumno actualizada = this.service.updateAlumno(id, dato);
            return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(actualizada);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping ("/{id}")
    @Transactional 
    public ResponseEntity <?> deleteAlumno (@PathVariable Long id){
        try {
            this.service.deleteAlumno(id);
            return ResponseEntity.ok(Map.of("message", "alumno con id "+id+" eliminado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    } 


}
