package com.example.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double tarifa;

    private LocalDate fechaAlta;

    private Double saldo;

    @Column(columnDefinition = "boolean default true")
    private boolean habilitada;

    // Relación many-to-many con Usuario
    @ManyToMany(mappedBy = "cuentas") // Referencia al atributo en Usuario
    private List<Usuario> usuarios;

    public Cuenta(Double tarifa, LocalDate fechaAlta, Double saldo, boolean habilitada) {
        this.tarifa = tarifa;
        this.fechaAlta = fechaAlta;
        this.saldo = saldo;
        this.habilitada = habilitada;
    }
}
