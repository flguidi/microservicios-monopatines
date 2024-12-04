package com.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CantidadMonopatinesEstadoDto {

    // 3. e) Cantidad de monopatines según su estado
    private int cantidadEnOperacion;
    private int cantidadEnMantenimiento;

}
