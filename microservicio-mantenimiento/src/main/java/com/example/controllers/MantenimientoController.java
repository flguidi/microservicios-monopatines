package com.example.controllers;

import com.example.dto.MantenimientoDto;
import com.example.services.MantenimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/mantenimientos")
public class MantenimientoController {
    @Autowired
    MantenimientoService mantenimientoService;

    @GetMapping("/")
    public List<MantenimientoDto> getAll() {
        return mantenimientoService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            MantenimientoDto mantenimiento = mantenimientoService.findById(id);
            return ResponseEntity.ok(mantenimiento);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el mantenimiento con ID " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody MantenimientoDto mantenimientoDto) {
        try {
            MantenimientoDto mantenimientoDtoCreado = mantenimientoService.save(mantenimientoDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(mantenimientoDtoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el mantenimiento");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MantenimientoDto mantenimiento) {
        try {
            MantenimientoDto mantenimientoDtoModificado = mantenimientoService.update(id, mantenimiento);
            return ResponseEntity.status(HttpStatus.CREATED).body(mantenimientoDtoModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el mantenimiento");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            MantenimientoDto mantenimientoDtoEliminado = mantenimientoService.delete(id);
            return ResponseEntity.ok(mantenimientoDtoEliminado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el mantenimiento con ID " + id);
        }
    }

    // -------------------------------------------- Servicios --------------------------------------------

    @PutMapping("/iniciar/{id}")
    public ResponseEntity<?> iniciarMantenimiento(@PathVariable Long id) {
        try {
            mantenimientoService.iniciarMantenimiento(id);
            return ResponseEntity.ok("Mantenimiento del monopatín " + id + " iniciado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatin con ID " + id);
        }
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<?> finalizarMantenimiento(@PathVariable Long id) {
        try {
            mantenimientoService.finalizarMantenimiento(id);
            return ResponseEntity.ok("Mantenimiento del monopatín " + id + " finalizado.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el monopatin con ID " + id);
        }
    }

    // 3. a) Generar reporte de monopatines para verificar mantenimiento.
    @GetMapping("/monopatines-reporte")
    public ResponseEntity<?> obtenerReporteMonopatines(@RequestParam boolean incluirPausa) {
        List<Object> respuesta = mantenimientoService.obtenerReporteMonopatines(incluirPausa);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

}
