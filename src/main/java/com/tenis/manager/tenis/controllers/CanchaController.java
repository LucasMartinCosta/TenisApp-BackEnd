package com.tenis.manager.tenis.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenis.manager.tenis.entities.Cancha;
import com.tenis.manager.tenis.services.CanchaService;

@RestController
@RequestMapping("/api/canchas")
public class CanchaController {

    @Autowired
    private CanchaService service;


    @GetMapping
    @Transactional (readOnly = true)
    public ResponseEntity <List <Cancha>> findAllCanchas (){
        List <Cancha> canchas = this.service.findAll();
        if(canchas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(canchas);
    }


    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Cancha> getCanchaByid (@PathVariable Long id){
        return this.service.findById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cancha> saveCancha (@RequestBody Cancha dato){
        Cancha nuevo = this.service.saveCancha(dato);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); 
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateCancha (@PathVariable Long id, @RequestBody Cancha dato){
        Optional <Cancha> canchOptional = this.service.findById(id); 

        if(canchOptional.isPresent()){
            Cancha cancha = canchOptional.get();
            cancha.setNombre(dato.getNombre());

            Cancha actualizada = this.service.updateCancha(id, dato);
            return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(actualizada);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping ("/{id}")
    @Transactional 
    public ResponseEntity <?> deleteCancha (@PathVariable Long id){
        try {
            this.service.deleteCancha(id);
            return ResponseEntity.ok(Map.of("message", "Precio con id "+id+" eliminado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}
