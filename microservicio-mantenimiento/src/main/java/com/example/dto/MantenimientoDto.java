package com.example.dto;

import com.example.entities.Mantenimiento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MantenimientoDto {
    private LocalDate inicio;
    private LocalDate fin;
    private String descripcion;

    public MantenimientoDto (Mantenimiento mantenimiento){
        this.inicio = mantenimiento.getInicio();
        this.fin = mantenimiento.getFin();
        this.descripcion = mantenimiento.getDescripcion();
    }
}
