package com.example.dto;

import com.example.entities.Cuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaDto {

    private Double tarifa;
    private LocalDate fechaAlta;
    private Double saldo;
    private boolean habilitada;

    public CuentaDto(Cuenta cuenta) {
        this.tarifa = cuenta.getTarifa();
        this.fechaAlta = cuenta.getFechaAlta();
        this.saldo = cuenta.getSaldo();
        this.habilitada = cuenta.isHabilitada();
    }

}
