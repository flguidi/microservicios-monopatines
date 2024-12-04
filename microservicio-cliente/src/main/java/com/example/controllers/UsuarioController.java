package com.example.controllers;

import com.example.dto.UsuarioDto;
import com.example.model.Monopatin;
import com.example.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping("/")
    public List<UsuarioDto> getAll() {
        return usuarioService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            UsuarioDto usuario = usuarioService.findById(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con ID " + id + ".");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody UsuarioDto usuarioDto) {
        try {
            UsuarioDto usuarioDtoCreado = usuarioService.save(usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDtoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el usuario.");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UsuarioDto usuarioDto) {
        try {
            UsuarioDto usuarioDtoModificado = usuarioService.update(id, usuarioDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDtoModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el usuario.");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            UsuarioDto cuentaDtoEliminado = usuarioService.delete(id);
            return ResponseEntity.ok(cuentaDtoEliminado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el usuario con ID " + id + ".");
        }
    }

    // 3. g) Cantidad de monopatines disponibles en una zona especificada.
    @GetMapping("/monopatines-cercanos")
    public ResponseEntity<?> obtenerMonopatinesCercanos(@RequestParam("latitud") Double latitud, @RequestParam("longitud") Double longitud) {
        try {
            List<Monopatin> monopatines = usuarioService.obtenerMonopatinesCercanos(latitud, longitud);
            return ResponseEntity.ok(monopatines);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al consultar monopatines cercanos.");
        }
    }

}
