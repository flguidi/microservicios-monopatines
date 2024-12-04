package com.example.utils;

import com.example.entities.Monopatin;
import com.example.repository.MonopatinRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CargaDatosMonopatin {

    @Bean
    CommandLineRunner cargarDatos(@Qualifier("monopatinRepository") MonopatinRepository monopatinRepository) {
        return args -> {
            log.info("Preloading " + monopatinRepository.save(new Monopatin((long) 1, "En parada", 12.3, 63.2, 23.6, 54.6)));
            log.info("Preloading " + monopatinRepository.save(new Monopatin((long) 2, "En mantenimiento", 23.4, 53.2, 53.2, 42.3)));
            log.info("Preloading " + monopatinRepository.save(new Monopatin((long) 3, "En uso", 85.1, 63.2, 42.3, 41.3)));
            log.info("Preloading " + monopatinRepository.save(new Monopatin((long) 4, "En parada", 12.3, 32.4, 23.6, 32.5)));
            log.info("Preloading " + monopatinRepository.save(new Monopatin((long) 5, "En parada", 56.3, 75.6, 32.56, 20.1)));
            log.info("Preloading " + monopatinRepository.save(new Monopatin((long) 6, "En uso", 96.3, 10.3, 23.5, 20.5)));
        };
    }

}
