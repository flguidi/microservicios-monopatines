package com.example.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    private Double tarifa;
    private LocalDate fechaAlta;
    private Double saldo;
}
