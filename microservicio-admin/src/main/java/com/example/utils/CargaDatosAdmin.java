package com.example.utils;

import com.example.entities.Admin;
import com.example.repository.AdminRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class CargaDatosAdmin {

    @Bean
    CommandLineRunner cargarDatos(@Qualifier("adminRepository") AdminRepository adminRepository) {
        return args -> {
            log.info("Preloading " + adminRepository.save(new Admin((long) 1, "Homero", "Simpson", "12398765", "1234")));
            log.info("Preloading " + adminRepository.save(new Admin((long) 2, "Pepe", "Argento", "56326598", "6598")));
            log.info("Preloading " + adminRepository.save(new Admin((long) 3, "Marge", "Simpson", "21345697", "52364")));
            log.info("Preloading " + adminRepository.save(new Admin((long) 4, "Susana", "Gimenez", "78546321", "74562")));
            log.info("Preloading " + adminRepository.save(new Admin((long) 5, "Mirta", "Legrand", "98765432", "23568")));
            log.info("Preloading " + adminRepository.save(new Admin((long) 6, "Moria", "Casan", "123659874", "98632")));
        };
    }
}
