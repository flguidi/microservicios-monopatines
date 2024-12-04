package com.example.dto;

import com.example.entities.Monopatin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonopatinDto {

    private String estado;
    private Double latitud;
    private Double longitud;
    private Double kmRecorridos;
    private Double tiempoUso;

    public MonopatinDto(Monopatin monopatin) {
        this.estado = monopatin.getEstado();
        this.latitud = monopatin.getLatitud();
        this.longitud = monopatin.getLongitud();
        this.kmRecorridos = monopatin.getKmRecorridos();
        this.tiempoUso = monopatin.getTiempoUso();
    }

}
