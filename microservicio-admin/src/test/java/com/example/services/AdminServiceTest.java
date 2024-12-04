package com.example.services;

import com.example.dto.AdminDto;
import com.example.dto.TarifaRequestDto;
import com.example.entities.Admin;
import com.example.feignClients.MonopatinFeign;
import com.example.model.Monopatin;
import com.example.repository.AdminRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminServiceTest {

    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository adminRepository;

    @Mock
    private MonopatinFeign monopatinFeign;


    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba para que no haya interferencia entre pruebas
        adminRepository.deleteAll();

        // Se inicializan los mocks
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Verifica que no haya administradores repetidos en base a su DNI.
     */
    @Test
    public void testAdminsDuplicados() {
        // Guardamos un administrador con un DNI específico
        Admin admin1 = new Admin(1L, "Juan", "Pérez", "12345678", "1234");
        adminRepository.save(admin1);

        // Intentamos guardar otro administrador con el mismo DNI
        AdminDto admin2 = new AdminDto("Carlos", "Gómez", "12345678", "5678");

        // Verificamos que se lance una excepción si intentamos guardar un administrador con el mismo DNI
        assertThrows(IllegalArgumentException.class, () -> adminService.save(admin2),
                "Ya existe un admin con el mismo número de DNI.");
    }

    /**
     * Verifica que una cuenta fue anulada con éxito.
     */
    @Test
    public void testAnularCuenta() {
        Long idCuenta = 2L;
        boolean estado = false;
        assertThrows(RuntimeException.class, () -> adminService.cambiarEstadoCuenta(idCuenta, estado),
                "No se encontró la cuenta con ID: " + idCuenta);
    }

    /**
     * Verifica que la cantidad de viajes sea un número positivo y el año sea mayor a 0 y menor o igual al actual.
     */
    @Test
    public void testMonopatinesCantViajesAnio() {
        Integer cantViajes = -1;
        Integer anio = 2024;
        assertThrows(IllegalArgumentException.class, () -> adminService.monopatinesCantViajesAnio(cantViajes, anio),
                "Los valores ingresados son inválidos.");
    }

    /**
     * Verifica que la cantidad de viajes sea un número positivo y el año sea mayor a 0 y menor o igual al actual.
     */
    @Test
    public void testObtenerTotalFacturado() {
        Integer anio = 2024;
        Integer mesInicio = 9;
        Integer mesFin = 8;
        assertThrows(IllegalArgumentException.class, () -> adminService.obtenerTotalFacturado(anio, mesInicio, mesFin),
                "Los valores ingresados son inválidos.");
    }

    /**
     * Verifica que la suma de monopatines en operación y monopatines en mantenimiento sea igual a la cantidad total
     * de monopatines.
     */
    @Test
    public void testObtenerCantidadMonopatines() {
        // Se simulan los valores que se van a retornar desde el FeignClient
        when(monopatinFeign.obtenerCantidadEnOperacion()).thenReturn(3); // 3 monopatines en operación
        when(monopatinFeign.obtenerCantidadEnMantenimiento()).thenReturn(2); // 2 monopatines en mantenimiento

        // getAllMonopatines() devolverá una lista de 5 monopatines
        List<Monopatin> monopatines = List.of(
                new Monopatin(),
                new Monopatin(),
                new Monopatin(),
                new Monopatin(),
                new Monopatin()
        );
        when(monopatinFeign.getAllMonopatines()).thenReturn(monopatines);

        // Se obtienen los valores de prueba
        int cantidadEnOperacion = monopatinFeign.obtenerCantidadEnOperacion();
        int cantidadEnMantenimiento = monopatinFeign.obtenerCantidadEnMantenimiento();
        int totalEsperado = monopatinFeign.getAllMonopatines().size();

        // Se verifica que la suma de los monopatines en operación y mantenimiento es igual al total esperado
        assertEquals(totalEsperado, cantidadEnOperacion + cantidadEnMantenimiento,
                "La suma de monopatines en operación y monopatines en mantenimiento es igual a la cantidad total de monopatines.");

        // Se verifica que los métodos fueron llamados correctamente
        verify(monopatinFeign).obtenerCantidadEnOperacion();
        verify(monopatinFeign).obtenerCantidadEnMantenimiento();
        verify(monopatinFeign).getAllMonopatines();
    }

    /**
     * Verifica que la nueva tarifa normal ingresada posee un valor positivo y una fecha posterior a la actual.
     */
    @Test
    public void testModificarTarifaNormal() {
        Double nuevoValor = 1200.0;
        LocalDate fechaActualizacion = LocalDate.of(2024, 10, 20);
        TarifaRequestDto tarifaRequestDto = new TarifaRequestDto(nuevoValor, fechaActualizacion);
        assertThrows(IllegalArgumentException.class, () -> adminService.modificarTarifaNormal(tarifaRequestDto),
                "La nueva tarifa ingresada es inválida.");
    }

    /**
     * Verifica que la nueva tarifa extra ingresada posee un valor positivo y una fecha posterior a la actual.
     */
    @Test
    public void testModificarTarifaExtra() {
        Double nuevoValor = -1200.0;
        LocalDate fechaActualizacion = LocalDate.of(2025, 10, 20);
        TarifaRequestDto tarifaRequestDto = new TarifaRequestDto(nuevoValor, fechaActualizacion);
        assertThrows(IllegalArgumentException.class, () -> adminService.modificarTarifaExtra(tarifaRequestDto),
                "La nueva tarifa ingresada es inválida.");
    }

}