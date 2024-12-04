package com.example.services;

import com.example.dto.*;
import com.example.entities.Admin;
import com.example.feignClients.CuentaFeign;
import com.example.feignClients.MonopatinFeign;
import com.example.feignClients.ParadaFeign;
import com.example.feignClients.ViajeFeign;
import com.example.mappers.AdminMapper;
import com.example.model.Monopatin;
import com.example.model.Parada;
import com.example.repository.AdminRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {

    @Autowired
    private final AdminRepository adminRepository;
    @Autowired
    private final AdminMapper adminMapper;
    @Autowired
    private final CuentaFeign cuentaFeign;
    @Autowired
    private final MonopatinFeign monopatinFeign;
    @Autowired
    private final ParadaFeign paradaFeign;
    @Autowired
    private final ViajeFeign viajeFeign;

    @Transactional(readOnly = true)
    public List<AdminDto> findAll() {
        return this.adminRepository.findAll()
                .stream().map(AdminDto::new).toList();
    }

    @Transactional(readOnly = true)
    public AdminDto findById(Long id) {
        return this.adminRepository.findById(id)
                .map(AdminDto::new)
                .orElseThrow(() -> new RuntimeException("Administrador con ID " + id + " no encontrado."));
    }

    @Transactional
    public AdminDto save(AdminDto adminDto) {
        if (adminDto.getNombre().isEmpty() || adminDto.getApellido().isEmpty() ||
                adminDto.getDni().isEmpty() || adminDto.getContrasenia().isEmpty()) {
            throw new RuntimeException("Todos los campos son requeridos para crear un administrador.");
        }

        if (adminRepository.existsByDni(adminDto.getDni())) {
            log.info("Ya existe un admin con el mismo número de DNI.");
            throw new IllegalArgumentException("Ya existe un admin con el mismo número de DNI.");
        }

        // Convertir AdminDTO a entidad Admin
        Admin nuevoAdmin = adminMapper.toAdmin(adminDto);

        // Si pasa las validaciones, guardar el administrador en base de datos
        Admin guardado = adminRepository.save(nuevoAdmin);

        return adminMapper.toAdminDto(guardado);
    }

    public AdminDto update(Long id, AdminDto adminDto) {
        if (adminDto.getNombre().isEmpty() || adminDto.getApellido().isEmpty() ||
                adminDto.getDni().isEmpty() || adminDto.getContrasenia().isEmpty()) {
            throw new RuntimeException("Todos los campos son requeridos para modificar un administrador.");
        }

        // Buscar el admin por ID
        Admin adminExistente = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador con ID " + id + " no encontrado."));

        // Actualizar los campos del admin con los datos del DTO
        adminExistente.setNombre(adminDto.getNombre());
        adminExistente.setApellido(adminDto.getApellido());
        adminExistente.setDni(adminDto.getDni());
        adminExistente.setContrasenia(adminDto.getContrasenia());

        // Guardar los cambios
        adminRepository.save(adminExistente);

        // Devolver el adminDto
        return adminMapper.toAdminDto(adminExistente);
    }

    public AdminDto delete(Long id) {
        Admin adminExistente = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrador con ID " + id + " no encontrado."));

        // Eliminar el admin
        adminRepository.delete(adminExistente);

        // Devolver el admin eliminado
        return adminMapper.toAdminDto(adminExistente);
    }



    // ------------------------------------------------ Servicios ------------------------------------------------

    // 3. b) Anula cuentas para inhabilitar el uso momentáneo de las mismas.
    @Transactional
    public void cambiarEstadoCuenta(Long id, boolean estado) {
        try {
            cuentaFeign.cambiarEstadoCuenta(id, estado);
        } catch (FeignException.NotFound e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public Monopatin crearMonopatin(Monopatin monopatin) {
        try {
            return monopatinFeign.createMonopatin(monopatin);
        } catch (Exception e) {
            throw new RuntimeException("El monopatín no pudo ser creado.");
        }
    }

    @Transactional
    public Monopatin modificarMonopatin(Monopatin monopatin, Long id) {
        try {
            return monopatinFeign.updateMonopatin(monopatin, id);
        } catch (Exception e) {
            throw new RuntimeException("El monopatín no pudo ser modificado.");
        }
    }

    @Transactional
    public Monopatin eliminarMonopatin(Long id) {
        try {
            return monopatinFeign.deleteMonopatin(id);
        } catch (Exception e) {
            throw new RuntimeException("El monopatín no pudo ser eliminado.");
        }
    }

    @Transactional
    public Parada crearParada(Parada parada) {
        try {
            return paradaFeign.createParada(parada);
        } catch (Exception e) {
            throw new RuntimeException("La parada no pudo ser creada.");
        }
    }

    @Transactional
    public Parada modificarParada(Parada parada, Long id) {
        try {
            return paradaFeign.updateParada(parada, id);
        } catch (Exception e) {
            throw new RuntimeException("La parada no pudo ser modificada.");
        }
    }

    @Transactional
    public Parada eliminarParada(Long id) {
        try {
            return paradaFeign.deleteParada(id);
        } catch (Exception e) {
            throw new RuntimeException("La parada no pudo ser eliminada.");
        }
    }

    // 3. c) Cantidad de monopatines con más viajes que una cantidad dada en un determinado año.
    public List<CantViajesMonopatinDto> monopatinesCantViajesAnio(Integer cantViajes, Integer anio) {
        if (anio < 0 || anio > Year.now().getValue()) {
            throw new IllegalArgumentException("Año inválido.");
        } else if (cantViajes < 0) {
            throw new IllegalArgumentException("Cantidad inválida de viajes.");
        }
        return viajeFeign.monopatinesCantViajesAnio(cantViajes, anio);
    }

    // 3. d) Obtener total facturado en un rango de meses un cierto año.
    public TotalFacturadoDto obtenerTotalFacturado(Integer anio, Integer mesInicio, Integer mesFin) {
        if (anio < 0 || anio > Year.now().getValue()) {
            throw new IllegalArgumentException("Año inválido.");
        } else if ((mesInicio < 1 || mesInicio > 12) || (mesFin < 1 || mesFin > 12) || mesInicio > mesFin) {
            throw new IllegalArgumentException("Rango inválido de meses.");
        }
        return viajeFeign.obtenerTotalFacturado(anio, mesInicio, mesFin);
    }
    
    // 3. e) Cantidad de monopatines según su estado
    public CantidadMonopatinesEstadoDto obtenerCantidadMonopatines() {
        int cantidadEnOperacion = monopatinFeign.obtenerCantidadEnOperacion();
        int cantidadEnMantenimiento = monopatinFeign.obtenerCantidadEnMantenimiento();
        return new CantidadMonopatinesEstadoDto(cantidadEnOperacion, cantidadEnMantenimiento);
    }

    // 3. f) Modifica la tarifa normal de los viajes.
    @Transactional
    public void modificarTarifaNormal(TarifaRequestDto tarifaDto) {
        if (tarifaDto.getNuevoValor() < 0 || tarifaDto.getFechaActualizacion().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La nueva tarifa ingresada es inválida.");
        }

        try {
            viajeFeign.modificarTarifaNormal(tarifaDto);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar la tarifa normal.");
        }
    }

    // 3. f) Modifica la tarifa extra de los viajes.
    @Transactional
    public void modificarTarifaExtra(TarifaRequestDto tarifaDto) {
        if (tarifaDto.getNuevoValor() < 0 || tarifaDto.getFechaActualizacion().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La nueva tarifa ingresada es inválida.");
        }

        try {
            viajeFeign.modificarTarifaExtra(tarifaDto);
        } catch (Exception e) {
            throw new RuntimeException("Error al modificar la tarifa extra.");
        }
    }

}
