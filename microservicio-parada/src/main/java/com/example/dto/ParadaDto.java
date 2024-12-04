package com.example.dto;

import com.example.entities.Parada;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParadaDto {

    private String nombre;
    private double latitud;
    private double longitud;
    private Integer cantMaxMonopatin;

    public ParadaDto(Parada parada) {
        this.nombre = parada.getNombre();
        this.latitud = parada.getLatitud();
        this.longitud = parada.getLongitud();
        this.cantMaxMonopatin = parada.getCantMaxMonopatin();
    }
}
