package com.example.services;

import com.example.dto.MonopatinConPausaDto;
import com.example.dto.MonopatinSinPausaDto;
import com.example.entities.Viaje;
import com.example.repository.ViajeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ViajeServiceTest {

    @Autowired
    private ViajeRepository viajeRepository;
    @Autowired
    private ViajeService viajeService;

    private Viaje v1, v2; // Viajes para hacer pruebas

    @BeforeEach
    void setUp() {
        viajeRepository.deleteAll();

        v1 = new Viaje(
                1L,
                LocalDateTime.of(2024, 11, 19, 10, 11, 16, 0), // Inicio
                LocalDateTime.of(2024, 11, 19, 10, 30, 12, 0), // Fin
                10.0,  // Km recorridos
                5,     // Minutos de pausa
                100.0, // Tarifa normal
                20.0,  // Tarifa extra
                15,    // Máximo tiempo de pausa
                1L,    // ID Monopatín
                1L     // ID Cuenta
        );

        v2 = new Viaje(
                2L,
                LocalDateTime.of(2024, 11, 21, 8, 0, 0, 0),   // Inicio
                LocalDateTime.of(2024, 11, 21, 8, 43, 13, 0), // Fin
                14.0,  // Km recorridos
                7,     // Minutos de pausa
                100.0, // Tarifa normal
                20.0,  // Tarifa extra
                15,    // Máximo tiempo de pausa
                1L,    // ID Monopatín
                2L     // ID Cuenta
        );

        viajeRepository.save(v1);
        viajeRepository.save(v2);
    }

    @Test
    void testReporteMonopatinesSinPausa() {
        // Lista esperada
        MonopatinSinPausaDto dto = new MonopatinSinPausaDto(1L, 24.0);
        List<MonopatinSinPausaDto> listaEsperada = List.of(dto);

        // Lista obtenida
        List<MonopatinSinPausaDto> listaObtenida = viajeService.reporteMonopatinesSinPausa();

        // Se verifica que el resultado es correcto
        assertTrue(listaEsperada.containsAll(listaObtenida));
        assertEquals(1, listaObtenida.size());
        assertEquals(listaEsperada.getFirst().getKmRecorridos(), v1.getKmRecorridos() + v2.getKmRecorridos());
    }

    @Test
    void testReporteMonopatinesConPausa() {
        // Lista esperada
        MonopatinConPausaDto dto = new MonopatinConPausaDto(1L, 24.0, 12L);
        List<MonopatinConPausaDto> listaEsperada = List.of(dto);

        // Lista obtenida
        List<MonopatinConPausaDto> listaObtenida = viajeService.reporteMonopatinesConPausa();

        // Se verifica que el resultado es correcto
        assertTrue(listaEsperada.containsAll(listaObtenida));
        assertEquals(1, listaObtenida.size());
        assertEquals(listaEsperada.getFirst().getKmRecorridos(), v1.getKmRecorridos() + v2.getKmRecorridos());
        assertEquals(listaEsperada.getFirst().getTiempoPausa(), v1.getMinPausa() + v2.getMinPausa());
    }

}
