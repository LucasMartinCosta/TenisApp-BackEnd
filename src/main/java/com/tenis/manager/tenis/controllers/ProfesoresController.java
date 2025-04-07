package com.tenis.manager.tenis.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenis.manager.tenis.entities.Profesor;
import com.tenis.manager.tenis.services.ProfesorService;

@Controller
@RequestMapping("/api/profesores")
public class ProfesoresController {

    @Autowired
    private ProfesorService service;


    @GetMapping
    @Transactional (readOnly = true)
    public ResponseEntity <List <Profesor>> findAllProfesores (){
        List <Profesor> profesores = this.service.findAll();
        if(profesores.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(profesores);
    }


    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Profesor> getProfesoresByid (@PathVariable Long id){
        return this.service.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profesor> saveProfesor (@RequestBody Profesor dato){
        Profesor nuevo = this.service.saveProfesor(dato);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); 
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateProfesor (@PathVariable Long id, @RequestBody Profesor dato){
        Optional <Profesor> profesorOptional = this.service.findById(id); 

        if(profesorOptional.isPresent()){
            Profesor profe = profesorOptional.get();
            profe.setNombre(dato.getNombre());
            profe.setCelular(dato.getCelular());
            profe.setFechaNacimiento(dato.getFechaNacimiento());

            Profesor actualizada = this.service.updateProfesor(id, dato);
            return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(actualizada);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping ("/{id}")
    @Transactional 
    public ResponseEntity <?> deleteProfesor (@PathVariable Long id){
        try {
            this.service.deleteProfesor(id);
            return ResponseEntity.ok(Map.of("message", "profesor con id "+id+" eliminado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
