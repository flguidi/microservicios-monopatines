package com.example.controllers;

import com.example.dto.*;
import com.example.model.Monopatin;
import com.example.model.Parada;
import com.example.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/admins")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/")
    public List<AdminDto> getAll() {
        return adminService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            AdminDto admin = adminService.findById(id);
            return ResponseEntity.ok(admin);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el administrador con ID " + id + ".");
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody AdminDto adminDto) {
        try {
            AdminDto adminDtoCreado = adminService.save(adminDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(adminDtoCreado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Ya existe un admin con el mismo número de DNI.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el administrador.");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody AdminDto admin) {
        try {
            AdminDto adminDtoModificado = adminService.update(id, admin);
            return ResponseEntity.status(HttpStatus.CREATED).body(adminDtoModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el administrador.");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            AdminDto adminDtoEliminado = adminService.delete(id);
            return ResponseEntity.ok(adminDtoEliminado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el administrador con ID " + id + ".");
        }
    }

    // --------------------------------- Servicios ---------------------------------

    // 3. b) Habilita o inhabilita una cuenta dada.
    @PutMapping("/estado-cuenta/{id}/estado/{estado}")
    public ResponseEntity<?> cambiarEstadoCuenta(@PathVariable Long id, @PathVariable boolean estado) {
        try {
            adminService.cambiarEstadoCuenta(id, estado);
            return ResponseEntity.status(HttpStatus.OK).body("Cuenta con ID " + id + " habilitada = " + estado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró la cuenta con ID " + id + ".");
        }
    }

    @PostMapping("/crear-monopatin/")
    public ResponseEntity<?> crearMonopatin(@RequestBody Monopatin monopatin) {
        try {
            Monopatin monopatinCreado = adminService.crearMonopatin(monopatin);
            return ResponseEntity.status(HttpStatus.CREATED).body(monopatinCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el monopatín.");
        }
    }

    @PutMapping("/modificar-monopatin/id/{id}")
    public ResponseEntity<?> modificarMonopatin(@RequestBody Monopatin monopatin, @PathVariable Long id) {
        try {
            Monopatin monopatinModificado = adminService.modificarMonopatin(monopatin, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(monopatinModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el monopatín con ID " + id + ".");
        }
    }

    @DeleteMapping("/eliminar-monopatin/id/{id}")
    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id) {
        try {
            Monopatin monopatinEliminado = adminService.eliminarMonopatin(id);
            return ResponseEntity.status(HttpStatus.OK).body(monopatinEliminado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el monopatín con ID " + id + ".");
        }
    }

    @PostMapping("/crear-parada/")
    public ResponseEntity<?> crearParada(@RequestBody Parada parada) {
        try {
            Parada paradaCreada = adminService.crearParada(parada);
            return ResponseEntity.status(HttpStatus.CREATED).body(paradaCreada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear la parada.");
        }
    }

    @PutMapping("/modificar-parada/id/{id}")
    public ResponseEntity<?> modificarParada(@RequestBody Parada parada, @PathVariable Long id) {
        try {
            Parada paradaModificada = adminService.modificarParada(parada, id);
            return ResponseEntity.status(HttpStatus.CREATED).body(paradaModificada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar la parada con ID " + id + ".");
        }
    }

    @DeleteMapping("/eliminar-parada/id/{id}")
    public ResponseEntity<?> eliminarParada(@PathVariable Long id) {
        try {
            Parada paradaEliminada = adminService.eliminarParada(id);
            return ResponseEntity.status(HttpStatus.OK).body(paradaEliminada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar la parada con ID " + id + ".");
        }
    }

    // 3. c) Obtener cantidad de monopatines con más de X viajes en un determinado año.
    @GetMapping("/cant-monopatines/cant-viajes/{cantViajes}/anio/{anio}")
    public ResponseEntity<?> monopatinesCantViajesAnio(@PathVariable Integer cantViajes, @PathVariable Integer anio) {
        List<CantViajesMonopatinDto> respuesta = adminService.monopatinesCantViajesAnio(cantViajes, anio);
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // 3. d) Obtener total facturado en un rango de meses un cierto año.
    @GetMapping("/total-facturado")
    public ResponseEntity<?> obtenerTotalFacturado(@RequestParam Integer anio, @RequestParam Integer mesInicio, @RequestParam Integer mesFin) {
        TotalFacturadoDto total = adminService.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return new ResponseEntity<>(total, HttpStatus.OK);
    }

    // 3. e) Obtener cantidad de monopatines según su estado.
    @GetMapping("/monopatines/cantidad-estado")
    public ResponseEntity<?> obtenerCantidadMonopatines() {
        CantidadMonopatinesEstadoDto respuesta = adminService.obtenerCantidadMonopatines();
        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }

    // 3. f) Modifica la tarifa normal de los viajes.
    @PutMapping("/modificar-tarifa-normal/")
    public ResponseEntity<?> modificarTarifaNormal(@RequestBody TarifaRequestDto tarifaDto) {
        try {
            adminService.modificarTarifaNormal(tarifaDto);
            return ResponseEntity.status(HttpStatus.OK).body("Nuevo valor de tarifa normal: " + tarifaDto.getNuevoValor());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el valor de la tarifa normal.");
        }
    }

    // 3. f) Modifica la tarifa extra de los viajes.
    @PutMapping("/modificar-tarifa-extra/")
    public ResponseEntity<?> modificarTarifaExtra(@RequestBody TarifaRequestDto tarifaDto) {
        try {
            adminService.modificarTarifaExtra(tarifaDto);
            return ResponseEntity.status(HttpStatus.OK).body("Nuevo valor de tarifa extra: " + tarifaDto.getNuevoValor());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el valor de la tarifa extra.");
        }
    }

}
