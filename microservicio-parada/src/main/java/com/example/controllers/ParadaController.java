package com.example.controllers;

import com.example.dto.ParadaDto;
import com.example.services.ParadaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/paradas")
public class ParadaController {
    @Autowired
    ParadaService paradaService;

    @GetMapping("/")
    public List<ParadaDto> getAll() {
        return paradaService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            ParadaDto parada = paradaService.findById(id);
            return ResponseEntity.ok(parada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la parada con ID " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ParadaDto paradaDto) {
        try {
            ParadaDto paradaDtoCreado = paradaService.save(paradaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(paradaDtoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la parada");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody ParadaDto parada) {
        try {
            ParadaDto paradaDtoModificado = paradaService.update(id, parada);
            return ResponseEntity.status(HttpStatus.CREATED).body(paradaDtoModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la parada");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            ParadaDto paradaDtoEliminado = paradaService.delete(id);
            return ResponseEntity.ok(paradaDtoEliminado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la parada con ID " + id);
        }
    }

}
