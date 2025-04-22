package com.tenis.manager.tenis.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tenis.manager.tenis.entities.Alquiler;
import com.tenis.manager.tenis.entities.Alumno;
import com.tenis.manager.tenis.entities.Clase;
import com.tenis.manager.tenis.entities.Profesor;
import com.tenis.manager.tenis.entities.Reserva;
import com.tenis.manager.tenis.repositories.AlquilerRepository;
import com.tenis.manager.tenis.repositories.AlumnoRepo;
import com.tenis.manager.tenis.repositories.CanchaRepository;
import com.tenis.manager.tenis.repositories.ClaseRepository;
import com.tenis.manager.tenis.repositories.ProfeRepo;
import com.tenis.manager.tenis.repositories.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepo; 

    @Autowired 
    private ClaseRepository claseRepo; 

    @Autowired
    private AlquilerRepository AlqRepo; 

    @Autowired
    private CanchaRepository CanchaRepo; 

    @Autowired
    private AlumnoRepo alumnoRepo; 

    @Autowired
    private ProfeRepo profeRepo; 




    // Obtener todas las reservas
    public List<Reserva> findAll() {
        return (List<Reserva>) reservaRepo.findAll();
    }

    // Obtener una reserva por ID
    public Optional<Reserva> findByIdReserva(Long id) {
        return reservaRepo.findById(id);
    }

    // Guardar una reserva (Clase o Alquiler)
   // public Reserva saveReserva(Reserva dato) {
     //   return reservaRepo.save(dato);
    //}

    public Reserva guardarReserva(Map<String, Object> datosReserva) {
        String tipo = (String) datosReserva.get("tipo");
    
        Reserva nuevaReserva;
        
    if ("CLASE".equals(tipo)) {
        nuevaReserva = new Clase();

        // Asignar los datos específicos para Clase
        List<Long> alumnoIds = (List<Long>) datosReserva.get("alumnos");
        List<Alumno> alumnos = alumnoRepo.findAllById(alumnoIds); // Busca todos los alumnos por sus IDs
        ((Clase) nuevaReserva).setAlumnos(alumnos);

        Long profesorId = ((Number) datosReserva.get("profesor")).longValue();
        Profesor profe = profeRepo.findById(profesorId).orElseThrow(() ->
            new IllegalArgumentException("Profesor con ID " + profesorId + " no existe"));
        ((Clase) nuevaReserva).setProfe(profe);

    } else if ("ALQUILER".equals(tipo)) {
        nuevaReserva = new Alquiler();

        // Asignar los datos específicos para Alquiler
        Double valor = ((Number) datosReserva.get("valor")).doubleValue(); // Conversión a Double
        ((Alquiler) nuevaReserva).setValor(valor);

        String nombre = (String) datosReserva.get("nombre");
        nuevaReserva.setNombre(nombre);

    } else {
        throw new IllegalArgumentException("Tipo de reserva no válido");
    }
    
        // Rellenar datos comunes
        nuevaReserva.setFecha(LocalDate.parse((String) datosReserva.get("fecha"), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        nuevaReserva.setInicio(LocalTime.parse((String) datosReserva.get("inicio")));
        nuevaReserva.setFinalizacion(LocalTime.parse((String) datosReserva.get("finalizacion")));
    
        // Conversión de "cancha" de Integer a Long
        try {
            Long canchaId = Long.valueOf((Integer) datosReserva.get("cancha")); // Conversión explícita
            nuevaReserva.setCancha(CanchaRepo.findById(canchaId).orElseThrow(() -> 
                new IllegalArgumentException("La cancha con ID " + canchaId + " no existe")));
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("El ID de la cancha debe ser un número válido", e);
        }
    
        return reservaRepo.save(nuevaReserva);
    }
    

    // Actualizar una reserva
    public Reserva updateReserva(Long id, Reserva dato) {
        return reservaRepo.findById(id).map(reserva -> {
            reserva.setCancha(dato.getCancha());
            reserva.setFecha(dato.getFecha());
            reserva.setInicio(dato.getInicio());
            reserva.setFinalizacion(dato.getFinalizacion());
            reserva.setNombre(dato.getNombre());

            // Si es una Clase, aseguramos que tenga alumnos y profesor
            if (reserva instanceof Clase clase && dato instanceof Clase nuevaClase) {
                clase.setAlumnos(nuevaClase.getAlumnos());
                clase.setProfe(nuevaClase.getProfe());
            }

            // Si es un Alquiler, aseguramos que tenga el valor
            if (reserva instanceof Alquiler alquiler && dato instanceof Alquiler nuevoAlquiler) {
                alquiler.setValor(nuevoAlquiler.getValor());
            }

            return reservaRepo.save(reserva);
        }).orElseThrow(() -> new RuntimeException("Reserva con ID " + id + " no encontrada"));
    }

    // Eliminar una reserva
    public void deleteReserva(Long id) {
        Reserva reserva = reservaRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Reserva con ID " + id + " no encontrada"));
        reservaRepo.delete(reserva);
    }

    // Obtener solo las reservas de tipo Alquiler
    public List<Alquiler> findAlquileres() {
        return AlqRepo.findAll();
    }

    // Obtener solo las reservas de tipo Clase
    public List<Clase> findClases() {
        return claseRepo.findAll(); 
    }

    


}
