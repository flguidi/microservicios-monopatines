package com.example.utils;

import com.example.entities.Mantenimiento;
import com.example.repository.MantenimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
@Slf4j
public class CargaDatosMantenimiento {

    @Bean
    CommandLineRunner cargarDatos(@Qualifier("mantenimientoRepository") MantenimientoRepository mantenimientoRepository) {
        return args -> {
            log.info("Preloading " + mantenimientoRepository.save(new Mantenimiento((long) 1, LocalDate.of(2024, 6, 5), LocalDate.of(2024, 7, 25), "Se arregló las ruedas")));
            log.info("Preloading " + mantenimientoRepository.save(new Mantenimiento((long) 2, LocalDate.of(2024, 5, 5), LocalDate.of(2024, 6, 4), "Se reinició sistema")));
            log.info("Preloading " + mantenimientoRepository.save(new Mantenimiento((long) 3, LocalDate.of(2023, 3, 5), LocalDate.of(2023, 4, 13), "Se reparó la manija")));
            log.info("Preloading " + mantenimientoRepository.save(new Mantenimiento((long) 4, LocalDate.of(2021, 12, 5), LocalDate.of(2021, 12, 6), "Se reseteó los kms")));
            log.info("Preloading " + mantenimientoRepository.save(new Mantenimiento((long) 5, LocalDate.of(2024, 8, 5), LocalDate.of(2024, 9, 6), "Se hizo mantenimiento")));
            log.info("Preloading " + mantenimientoRepository.save(new Mantenimiento((long) 6, LocalDate.of(2022, 3, 5), LocalDate.of(2022, 4, 10), "Se realizó pintura")));
        };
    }

}
