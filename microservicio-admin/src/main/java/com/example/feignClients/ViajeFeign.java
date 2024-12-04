package com.example.feignClients;

import com.example.dto.CantViajesMonopatinDto;
import com.example.dto.TarifaRequestDto;
import com.example.dto.TotalFacturadoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "microservicio-viaje")
public interface ViajeFeign {

    // 3. c) Cantidad de monopatines con más de X viajes en un determinado año.
    @GetMapping("api/viajes/cant-monopatines/cant-viajes/{cantViajes}/anio/{anio}")
    List<CantViajesMonopatinDto> monopatinesCantViajesAnio(@PathVariable Integer cantViajes, @PathVariable Integer anio);

    // 3. d) Obtener total facturado en un rango de meses un cierto año.
    @GetMapping("api/viajes/total-facturado")
    TotalFacturadoDto obtenerTotalFacturado(@RequestParam Integer anio, @RequestParam Integer mesInicio, @RequestParam Integer mesFin);

    // 3. f) Modifica la tarifa normal de los viajes.
    @PutMapping("api/viajes/tarifa-normal/")
    void modificarTarifaNormal(@RequestBody TarifaRequestDto tarifaRequestDto);

    // 3. f) Modifica la tarifa extra de los viajes.
    @PutMapping("api/viajes/tarifa-extra/")
    void modificarTarifaExtra(@RequestBody TarifaRequestDto tarifaRequestDto);

}
