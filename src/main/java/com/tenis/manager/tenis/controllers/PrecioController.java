package com.tenis.manager.tenis.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
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

import com.tenis.manager.tenis.entities.Precio;
import com.tenis.manager.tenis.services.precioService;



@RestController
@RequestMapping("/api/precios")
public class PrecioController {

    @Autowired
    private precioService service; 

    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<List<Precio>> findAllPrecios() {
        List<Precio> precios = this.service.findAll();
        if (precios.isEmpty()) {
            return ResponseEntity.noContent().build(); // Devuelve 204 si la lista está vacía
        }
        return ResponseEntity.ok(precios); // Devuelve 200 con la lista de precios
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<Precio> getPrecioById(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok) // Si existe, devuelve 200 con el precio
            .orElse(ResponseEntity.notFound().build()); // Si no existe, devuelve 404
    }

    @PostMapping
    public ResponseEntity <Precio> savePrecio (@RequestBody Precio dato){
        Precio nuevo = service.savePrecio(dato);  
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo); 
    }

    @PutMapping ("/{id}")
    @Transactional 
    public ResponseEntity <?> updatePrecio (@PathVariable Long id, @RequestBody Precio dato){


        Optional <Precio> precio = this.service.findById(id);

        if(precio.isPresent()){
            Precio nuevo = precio.get();
            nuevo.setNombre(dato.getNombre());
            nuevo.setPrecio(dato.getPrecio());

            Precio precioActualizado = this.service.updatePrecio(id, nuevo);

            return ResponseEntity 
                                    .status(HttpStatus.CREATED)
                                    .body(precioActualizado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity <?> deletePrecio (@PathVariable Long id){
        try {
            this.service.deletPrecio(id);
            return ResponseEntity.ok(Map.of("message", "Precio con id "+id+ "eliminado"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
