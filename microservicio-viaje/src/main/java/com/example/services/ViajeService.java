package com.example.services;

import com.example.dto.*;
import com.example.entities.Tarifa;
import com.example.entities.Viaje;
import com.example.mappers.ViajeMapper;
import com.example.repository.ViajeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViajeService {

    @Autowired
    ViajeRepository viajeRepository;
    @Autowired
    ViajeMapper viajeMapper;

    @Transactional
    public List<ViajeDto> findAll() {
        return this.viajeRepository.findAll()
                .stream().map(ViajeDto::new).toList();
    }

    @Transactional
    public ViajeDto findById(Long id){
        return this.viajeRepository.findById(id)
                .map(ViajeDto::new)
                .orElseThrow(()-> new RuntimeException("Viaje con ID " + id + " no encontrado."));
    }

    @Transactional
    public ViajeDto save(ViajeDto viajeDto) {
        // Convierte LocalDateTime a String para JSON
        String inicioStr = viajeDto.getInicio().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String finStr = viajeDto.getFin().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Crea un nuevo viaje con el nuevo formato
        Viaje viaje = new Viaje();
        viaje.setInicio(LocalDateTime.parse(inicioStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        viaje.setFin(LocalDateTime.parse(finStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        viaje.setKmRecorridos(viajeDto.getKmRecorridos());
        viaje.setMinPausa(viajeDto.getMinPausa());
        viaje.setMaxTiempoPausa(viajeDto.getMaxTiempoPausa());

        // Se verifica fecha de actualización de tarifas
        if (LocalDate.now().isAfter(Tarifa.getFechaActualizacion())) {
            // Se actualizan tarifas en clase Tarifa
            Tarifa.setTarifaNormal(Tarifa.getNuevaTarifaNormal());
            Tarifa.setTarifaExtra(Tarifa.getNuevaTarifaExtra());
            log.info("--- Se actualizaron las tarifas ---");
        }

        // Se establecen tarifas en el viaje
        viaje.setTarifaNormal(Tarifa.getTarifaNormal());
        viaje.setTarifaExtra(Tarifa.getTarifaExtra());

        // Guarda el viaje en la base de datos
        Viaje savedViaje = viajeRepository.save(viaje);

        // Convierte el viaje guaradado a ViajeDto
        return viajeMapper.toViajeDto(savedViaje);
    }

    public ViajeDto update(Long id, ViajeDto viajeDto) {
        if (viajeDto.getInicio() == null || viajeDto.getFin() == null || viajeDto.getKmRecorridos() == null ||
                viajeDto.getMinPausa() == null || viajeDto.getMaxTiempoPausa() == null) {
            throw new RuntimeException("Todos los campos son requeridos para modificar un viaje.");
        }

        // Buscar el viaje por ID
        Viaje viajeExistente = viajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje con ID " + id + " no encontrado."));

        // Actualizar los campos del viaje con los datos del DTO
        viajeExistente.setInicio(viajeDto.getInicio());
        viajeExistente.setFin(viajeDto.getFin());
        viajeExistente.setKmRecorridos(viajeDto.getKmRecorridos());
        viajeExistente.setMinPausa(viajeDto.getMinPausa());
        viajeExistente.setMaxTiempoPausa(viajeDto.getMaxTiempoPausa());

        // Guardar los cambios
        viajeRepository.save(viajeExistente);

        return viajeMapper.toViajeDto(viajeExistente);
    }

    public ViajeDto delete(Long id) {
        Viaje viajeExistente = viajeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Viaje con ID " + id + " no encontrado."));

        // Eliminar el viaje
        viajeRepository.delete(viajeExistente);

        // Devolver el viaje eliminado
        return viajeMapper.toViajeDto(viajeExistente);
    }

    // ------------------------------------------ Servicios ------------------------------------------

    // 3. a) Reporte con km recorridos de cada monopatín.
    public List<MonopatinSinPausaDto> reporteMonopatinesSinPausa() {
        return viajeRepository.getMonopatinesSinPausa();
    }

    // 3. a) Reporte con km recorridos y tiempo total de espera de cada monopatín.
    public List<MonopatinConPausaDto> reporteMonopatinesConPausa() {
        return viajeRepository.getMonopatinesConPausa();
    }

    // 3. c) Cantidad de monopatines con más de X viajes en un determinado año.
    public List<CantViajesMonopatinDto> monopatinesCantViajesAnio(Integer cantViajes, Integer anio) {
        return viajeRepository.monopatinesCantViajesAnio(cantViajes, anio);
    }

    // 3. d) Obtener total facturado en un rango de meses un cierto año.
    public TotalFacturadoDto obtenerTotalFacturado(Integer anio, Integer mesInicio, Integer mesFin) {
        List<Viaje> viajes = viajeRepository.obtenerViajesEnRango(anio, mesInicio, mesFin);

        double total = 0.0;
        TotalFacturadoDto totalFacturado = new TotalFacturadoDto(0.0);

        if (!viajes.isEmpty()) {
            for (Viaje viaje : viajes) {
                // Calcular la duración en minutos
                long minutos = Duration.between(viaje.getInicio(), viaje.getFin()).toMinutes();

                // Calcular el precio dependiendo del tiempo de pausa
                double precio;
                if (viaje.getMinPausa() > 15) {
                    // Si la pausa es mayor a 15 minutos, usar tarifaExtra
                    precio = viaje.getTarifaExtra() * minutos;
                } else {
                    // Si la pausa es menor o igual a 15 minutos, usar tarifaNormal
                    precio = viaje.getTarifaNormal() * minutos;
                }

                total += precio;
                totalFacturado.setTotalFacturado(total);
            }
        }

        return totalFacturado;
    }

    // 3. f) Modifica la tarifa normal de los viajes.
    public void modificarTarifaNormal(TarifaDto tarifaDto) {
        Tarifa.setNuevaTarifaNormal(tarifaDto.getNuevoValor());
        Tarifa.setFechaActualizacion(tarifaDto.getFechaActualizacion());
    }

    // 3. f) Modifica la tarifa extra de los viajes.
    public void modificarTarifaExtra(TarifaDto tarifaDto) {
        Tarifa.setNuevaTarifaExtra(tarifaDto.getNuevoValor());
        Tarifa.setFechaActualizacion(tarifaDto.getFechaActualizacion());
    }

}
