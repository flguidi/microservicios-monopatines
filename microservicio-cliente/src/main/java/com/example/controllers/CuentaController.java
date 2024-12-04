package com.example.controllers;

import com.example.dto.CuentaDto;
import com.example.services.CuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cuentas")
public class CuentaController {

    @Autowired
    CuentaService cuentaService;

    @GetMapping("/")
    public List<CuentaDto> getAll() {
        return cuentaService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            CuentaDto cuenta = cuentaService.findById(id);
            return ResponseEntity.ok(cuenta);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con ID " + id + ".");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CuentaDto cuentaDto) {
        try {
            CuentaDto cuentaDtoCreado = cuentaService.save(cuentaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cuentaDtoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la cuenta.");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody CuentaDto cuentaDto) {
        try {
            CuentaDto cuentaDtoModificado = cuentaService.update(id, cuentaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(cuentaDtoModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la cuenta.");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            CuentaDto cuentaDtoEliminado = cuentaService.delete(id);
            return ResponseEntity.ok(cuentaDtoEliminado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con ID " + id + ".");
        }
    }

    @PutMapping("/estado-cuenta/{id}/estado/{estado}")
    public ResponseEntity<?> cambiarEstadoCuenta(@PathVariable("id") Long id, @PathVariable("estado") boolean estado) {
        try {
            cuentaService.cambiarEstadoCuenta(id, estado);
            return ResponseEntity.status(HttpStatus.OK).body("Cuenta con ID " + id + " habilitada = " + estado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con ID " + id + ".");
        }
    }
}
