package com.example.feignClients;

import com.example.dto.MonopatinConPausaDto;
import com.example.dto.MonopatinSinPausaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "microservicio-viaje")
public interface ViajeFeign {

    @GetMapping("api/viajes/reporte-sin-pausa/")
    List<MonopatinSinPausaDto> reporteMonopatinesSinPausa();

    @GetMapping("api/viajes/reporte-con-pausa/")
    List<MonopatinConPausaDto> reporteMonopatinesConPausa();

}
