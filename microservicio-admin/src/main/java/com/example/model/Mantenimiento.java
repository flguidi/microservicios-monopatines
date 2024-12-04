package com.example.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Mantenimiento {
    private LocalDate inicio;
    private LocalDate fin;
    private String descripcion;
}
