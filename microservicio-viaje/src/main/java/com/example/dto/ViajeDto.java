package com.example.dto;

import com.example.entities.Viaje;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViajeDto {

    private LocalDateTime inicio;
    private LocalDateTime fin;
    private Double kmRecorridos;
    private Integer minPausa;
    private Integer maxTiempoPausa;

    public ViajeDto(Viaje viaje) {
        this.inicio = viaje.getInicio();
        this.fin = viaje.getFin();
        this.kmRecorridos = viaje.getKmRecorridos();
        this.minPausa = viaje.getMinPausa();
        this.maxTiempoPausa = viaje.getMaxTiempoPausa();
    }
}
