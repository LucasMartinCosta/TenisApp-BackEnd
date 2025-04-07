package com.tenis.manager.tenis.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.tenis.manager.tenis.entities.Alquiler;
import com.tenis.manager.tenis.entities.Clase;
import com.tenis.manager.tenis.entities.Reserva;
import com.tenis.manager.tenis.services.ReservaService;


@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService service; 


    //Obtiene todas las clases 
    @GetMapping("/clases")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Clase>> getAllClases() {
        List<Clase> clases = service.findClases();
        if (clases.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clases);
    }

    //Obtiene todos los alquileres
     @GetMapping("/alquileres")
    @Transactional(readOnly = true)
    public ResponseEntity<List<Alquiler>> getAllAlquileres() {
        List<Alquiler> alquileres = service.findAlquileres();
        if (alquileres.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(alquileres);
    }

    //Obtiene todas las reservas
    @GetMapping
    @Transactional (readOnly = true)
    public ResponseEntity <List <Reserva>> findAllReservas (){
        List <Reserva> reservas = this.service.findAll();
        if(reservas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reservas);
    }

    //Obtiene una reserva por id
    @GetMapping("/{id}")
@Transactional(readOnly = true)
public ResponseEntity<Reserva> getReservaByid(@PathVariable Long id) {
    return this.service.findByIdReserva(id)
        .map(reserva -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(reserva))
        .orElse(ResponseEntity.notFound().build());
}

    @PostMapping()
    public ResponseEntity<?> guardarReserva(@RequestBody Map<String, Object> datosReserva) {
        try {
            Reserva nuevaReserva = service.guardarReserva(datosReserva);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaReserva);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error inesperado: " + e.getMessage());
        }
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> updateReserva(@PathVariable Long id, @RequestBody Reserva dato){
        Optional <Reserva> reservaOptional = this.service.findByIdReserva(id);

        if(reservaOptional.isPresent()){
            Reserva reserva = reservaOptional.get();
            reserva.setCancha(dato.getCancha());
            reserva.setFecha(dato.getFecha());
            reserva.setInicio(dato.getInicio());
            reserva.setFinalizacion(dato.getFinalizacion());

            // Si es una Clase, aseguramos que tenga alumnos y profesor
            if (reserva instanceof Clase clase && dato instanceof Clase nuevaClase) {
                clase.setAlumnos(nuevaClase.getAlumnos());
                clase.setProfe(nuevaClase.getProfe());
            }

            // Si es un Alquiler, aseguramos que tenga el valor
            if (reserva instanceof Alquiler alquiler && dato instanceof Alquiler nuevoAlquiler) {
                alquiler.setValor(nuevoAlquiler.getValor());
            }

            Reserva actualizada = this.service.updateReserva(id, dato);
            return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(actualizada);
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping ("/{id}")
    @Transactional 
    public ResponseEntity <?> deleteReseva (@PathVariable Long id){
        try {
            this.service.deleteReserva(id);
            return ResponseEntity.ok(Map.of("message", "Reserva con id "+id+" eliminada"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
