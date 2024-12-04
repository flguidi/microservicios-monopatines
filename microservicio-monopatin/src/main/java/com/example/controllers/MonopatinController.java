package com.example.controllers;

import com.example.dto.MonopatinDto;
import com.example.services.MonopatinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinService monopatinService;

    @GetMapping("/")
    public List<MonopatinDto> getAll() {
        return monopatinService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            MonopatinDto monopatin = monopatinService.findById(id);
            return ResponseEntity.ok(monopatin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatín con ID " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody MonopatinDto monopatinDto) {
        try {
            MonopatinDto monopatinDtoCreado = monopatinService.save(monopatinDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(monopatinDtoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el monopatín");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MonopatinDto monopatin) {
        try {
            MonopatinDto monopatinDtoModificado = monopatinService.update(id, monopatin);
            return ResponseEntity.status(HttpStatus.CREATED).body(monopatinDtoModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el monopatín");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            MonopatinDto monopatinDtoEliminado = monopatinService.delete(id);
            return ResponseEntity.ok(monopatinDtoEliminado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatín con ID " + id);
        }
    }

    @PutMapping("/iniciar-mantenimiento/{id}")
    public ResponseEntity<?> iniciarMantenimiento(@PathVariable Long id) {
        try {
            monopatinService.establecerEstado(id, "En mantenimiento");
            return ResponseEntity.ok("Mantenimiento del monopatín " + id + " iniciado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatín con ID " + id);
        }
    }

    @PutMapping("/finalizar-mantenimiento/{id}")
    public ResponseEntity<?> finalizarMantenimiento(@PathVariable Long id) {
        try {
            monopatinService.establecerEstado(id, "En parada");
            return ResponseEntity.ok("Mantenimiento del monopatín " + id + " iniciado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatín con ID " + id);
        }
    }

    // ---------------------------------------------- Servicios ----------------------------------------------

    // 3. e) Devolver cantidad de monopatines según su estado (en operación)
    @GetMapping("/cantidad-operacion")
    public ResponseEntity<Integer> obtenerCantidadEnOperacion() {
        int cantidadEnOperacion = monopatinService.obtenerCantidadEnOperacion();
        return ResponseEntity.ok(cantidadEnOperacion);
    }

    // 3. e) Devolver cantidad de monopatines según su estado (en mantenimiento)
    @GetMapping("/cantidad-mantenimiento")
    public ResponseEntity<Integer> obtenerCantidadEnMantenimiento() {
        int cantidadEnMantenimiento = monopatinService.obtenerCantidadEnMantenimiento();
        return ResponseEntity.ok(cantidadEnMantenimiento);
    }

    // 3. g) Devolver cantidad de monopatines disponibles en una zona especificada.
    @GetMapping("/cercanos")
    public ResponseEntity<List<MonopatinDto>> obtenerMonopatinesCercanos(@RequestParam("latitud") Double latitud,
                                                                         @RequestParam("longitud") Double longitud) {
        List<MonopatinDto> monopatines = monopatinService.obtenerMonopatinesCercanos(latitud, longitud);
        return ResponseEntity.ok(monopatines);
    }

}
