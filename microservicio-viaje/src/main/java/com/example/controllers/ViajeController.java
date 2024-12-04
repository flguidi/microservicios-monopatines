package com.example.controllers;

import com.example.dto.*;
import com.example.services.ViajeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/viajes")
public class ViajeController {

    @Autowired
    ViajeService viajeService;

    @GetMapping("/")
    public List<ViajeDto> getAll() {
        return viajeService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            ViajeDto viaje = viajeService.findById(id);
            return ResponseEntity.ok(viaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el viaje con ID " + id);
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody ViajeDto viajeDto) {
        try {
            ViajeDto viajeDtoCreado = viajeService.save(viajeDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(viajeDtoCreado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo crear el viaje");
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ViajeDto viaje) {
        try {
            ViajeDto viajeDtoModificado = viajeService.update(id, viaje);
            return ResponseEntity.status(HttpStatus.CREATED).body(viajeDtoModificado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo modificar el viaje");
        }
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            ViajeDto viajeDtoEliminado = viajeService.delete(id);
            return ResponseEntity.ok(viajeDtoEliminado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el viaje con ID " + id);
        }
    }

    // -------------------------------------------------- Servicios --------------------------------------------------

    @PutMapping("/tarifa-normal/")
    public ResponseEntity<?> modificarTarifaNormal(@RequestBody TarifaDto tarifaDto) {
        viajeService.modificarTarifaNormal(tarifaDto);
        return ResponseEntity.ok("Nuevo valor de tarifa normal: " + tarifaDto.getNuevoValor());
    }

    @PutMapping("/tarifa-extra/")
    public ResponseEntity<?> modificarTarifaExtra(@RequestBody TarifaDto tarifaDto) {
        viajeService.modificarTarifaExtra(tarifaDto);
        return ResponseEntity.ok("Nuevo valor de tarifa extra: " + tarifaDto.getNuevoValor());
    }

    @GetMapping("/reporte-sin-pausa/")
    public ResponseEntity<?> reporteMonopatinesSinPausa() {
        List<MonopatinSinPausaDto> monopatines = viajeService.reporteMonopatinesSinPausa();
        return ResponseEntity.ok(monopatines);
    }

    @GetMapping("/reporte-con-pausa/")
    public ResponseEntity<?> reporteMonopatinesConPausa() {
        List<MonopatinConPausaDto> monopatines = viajeService.reporteMonopatinesConPausa();
        return ResponseEntity.ok(monopatines);
    }

    @GetMapping("/cant-monopatines/cant-viajes/{cantViajes}/anio/{anio}")
    public ResponseEntity<?> monopatinesCantViajesAnio(@PathVariable Integer cantViajes, @PathVariable Integer anio) {
        List<CantViajesMonopatinDto> monopatines = viajeService.monopatinesCantViajesAnio(cantViajes, anio);
        return ResponseEntity.ok(monopatines);
    }

    // 3. d) Obtener total facturado en un rango de meses un cierto año.
    @GetMapping("/total-facturado")
    public ResponseEntity<?> obtenerTotalFacturado(@RequestParam Integer anio, @RequestParam Integer mesInicio, @RequestParam Integer mesFin) {
        TotalFacturadoDto totalFacturado = viajeService.obtenerTotalFacturado(anio, mesInicio, mesFin);
        return ResponseEntity.ok(totalFacturado);
    }

}
