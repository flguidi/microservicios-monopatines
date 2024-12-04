package com.example.services;

import com.example.dto.MonopatinDto;
import com.example.repository.MonopatinRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MonopatinServiceTest {

    @Autowired
    private MonopatinService monopatinService;
    @Autowired
    MonopatinRepository monopatinRepository;

    @BeforeEach
    void setUp() {
        monopatinRepository.deleteAll();
    }

    @Test
    void testFindMonopatinesCercanos() {
        // Datos de prueba
        double latitud = 100.0;
        double longitud = 100.0;

        // Monopatines de prueba
        monopatinService.save(new MonopatinDto("En parada", 99.0, 99.0, 150.0, 120.0));
        monopatinService.save(new MonopatinDto("En parada", 100.1, 100.1, 200.0, 170.0));
        monopatinService.save(new MonopatinDto("Activo", 2050.0, 3500.0, 300.0, 250.0));
        monopatinService.save(new MonopatinDto("En parada", 2300.0, 2050.0, 400.0, 300.0));

        List<MonopatinDto> monopatinesEsperados = Arrays.asList(
                new MonopatinDto("En parada", 99.0, 99.0, 150.0, 120.0),
                new MonopatinDto("En parada", 100.1, 100.1, 200.0, 170.0)
        );

        List<MonopatinDto> monopatinesCercanos = monopatinService.obtenerMonopatinesCercanos(latitud, longitud);

        // Se verifican los resultados
        assertEquals(2, monopatinesCercanos.size());
        assertTrue(monopatinesCercanos.containsAll(monopatinesEsperados)); // Verifica si todos los elementos esperados están presentes
    }
}
