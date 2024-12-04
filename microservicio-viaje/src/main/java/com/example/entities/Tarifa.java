package com.example.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
public class Tarifa {

    @Getter
    @Setter
    private static Double tarifaNormal = 100.0;

    @Getter
    @Setter
    private static Double tarifaExtra = 120.0;

    @Getter
    @Setter
    private static LocalDate fechaActualizacion = LocalDate.now();

    @Getter
    @Setter
    private static Double nuevaTarifaNormal = Tarifa.tarifaNormal;

    @Getter
    @Setter
    private static Double nuevaTarifaExtra = Tarifa.tarifaExtra;

}
