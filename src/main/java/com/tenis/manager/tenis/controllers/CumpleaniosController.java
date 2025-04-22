package com.tenis.manager.tenis.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tenis.manager.tenis.entities.CumpleanieroDTO;
import com.tenis.manager.tenis.services.CumpleService;

@RestController
@RequestMapping("/api/cumpleanios")
public class CumpleaniosController {

    private final CumpleService cumpleService; 

    public CumpleaniosController(CumpleService cumpleaniosService) {
        this.cumpleService = cumpleaniosService;
    }

    @GetMapping("/hoy")
public ResponseEntity<List<CumpleanieroDTO>> obtenerCumpleanierosHoy() {
    List<CumpleanieroDTO> cumpleanieros = cumpleService.obtenerCumpleanierosHoy();
    if (cumpleanieros.isEmpty()) {
        return ResponseEntity.noContent().build();  // Si no hay cumpleaños, respondemos con HTTP 204
    }
    return ResponseEntity.ok(cumpleanieros);  // Si hay cumpleaños, respondemos con HTTP 200 y la lista
}

}
