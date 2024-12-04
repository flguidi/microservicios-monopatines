package com.example.services;

import com.example.dto.MantenimientoDto;
import com.example.dto.MonopatinConPausaDto;
import com.example.dto.MonopatinSinPausaDto;
import com.example.entities.Mantenimiento;
import com.example.feignClients.MonopatinFeign;
import com.example.feignClients.ViajeFeign;
import com.example.mappers.MantenimientoMapper;
import com.example.repository.MantenimientoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MantenimientoService {

    @Autowired
    MantenimientoRepository mantenimientoRepository;
    @Autowired
    MantenimientoMapper mantenimientoMapper;
    @Autowired
    MonopatinFeign monopatinFeign;
    @Autowired
    ViajeFeign viajeFeign;

    @Transactional(readOnly = true)
    public List<MantenimientoDto> findAll() {
        return this.mantenimientoRepository.findAll()
                .stream().map(MantenimientoDto::new).toList();
    }

    @Transactional(readOnly = true)
    public MantenimientoDto findById(Long id){
        return this.mantenimientoRepository.findById(id)
                .map(MantenimientoDto::new)
                .orElseThrow(()-> new RuntimeException("Manteniemiento con ID " + id + " no encontrado"));
    }

    @Transactional
    public MantenimientoDto save(MantenimientoDto mantenimientoDto) {
        System.out.println("Fecha Inicio: " + mantenimientoDto.getInicio());
        System.out.println("Fecha Fin: " + mantenimientoDto.getFin());
        System.out.println("Descripción: " + mantenimientoDto.getDescripcion());
        if (mantenimientoDto.getInicio() == null ||
                mantenimientoDto.getFin() == null ||
                mantenimientoDto.getDescripcion().isEmpty()) {
            throw new RuntimeException("Todos los campos son requeridos para crear un mantenimiento" + ".");
        }

        // Convertir LocalDateTime a String para JSON
        String inicioStr = mantenimientoDto.getInicio().format(DateTimeFormatter.ISO_LOCAL_DATE);
        String finStr = mantenimientoDto.getFin().format(DateTimeFormatter.ISO_LOCAL_DATE);

        Mantenimiento nuevoMantenimiento = mantenimientoMapper.toMantenimiento(mantenimientoDto);

        nuevoMantenimiento.setInicio(LocalDate.parse(inicioStr, DateTimeFormatter.ISO_LOCAL_DATE));
        nuevoMantenimiento.setFin(LocalDate.parse(finStr, DateTimeFormatter.ISO_LOCAL_DATE));
        nuevoMantenimiento.setDescripcion(mantenimientoDto.getDescripcion());

        // Si pasa las validaciones, guardar el estudiante en base de datos
        Mantenimiento guardado = mantenimientoRepository.save(nuevoMantenimiento);
        return mantenimientoMapper.toMantenimientoDto(guardado);
    }

    public MantenimientoDto update(Long id, MantenimientoDto mantenimientoDto) {
        if (mantenimientoDto.getInicio() == null || mantenimientoDto.getFin() == null || mantenimientoDto.getDescripcion().isEmpty()) {
            throw new RuntimeException("Todos los campos son requeridos para modificar un mantenimiento.");
        }
        Mantenimiento mantenimientoExistente = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento con ID " + id + " no encontrado"));

        mantenimientoExistente.setInicio(mantenimientoDto.getInicio());
        mantenimientoExistente.setFin(mantenimientoDto.getFin());
        mantenimientoExistente.setDescripcion(mantenimientoDto.getDescripcion());

        mantenimientoRepository.save(mantenimientoExistente);
        return mantenimientoMapper.toMantenimientoDto(mantenimientoExistente);
    }

    public MantenimientoDto delete(Long id) {
        Mantenimiento mantenimientoExistente = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("mantenimiento con ID " + id + " no encontrado"));

        mantenimientoRepository.delete(mantenimientoExistente);
        return mantenimientoMapper.toMantenimientoDto(mantenimientoExistente);
    }

    public void iniciarMantenimiento(Long id) {
        try {
            monopatinFeign.iniciarMantenimiento(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al iniciar mantenimiento del monopatín " + id + ".");
        }
    }

    public void finalizarMantenimiento(Long id) {
        try {
            monopatinFeign.finalizarMantenimiento(id);
        } catch (Exception e) {
            throw new RuntimeException("Error al iniciar mantenimiento del monopatín " + id + ".");
        }
    }

    // 3. a) Generar reporte de monopatines para verificar mantenimiento.
    public List<Object> obtenerReporteMonopatines(boolean incluirPausa) {
        if (incluirPausa) {
            List<MonopatinConPausaDto> monopatinesConPausa = viajeFeign.reporteMonopatinesConPausa();
            log.info(monopatinesConPausa.toString());
            return new ArrayList<>(monopatinesConPausa);
        } else {
            List<MonopatinSinPausaDto> monopatinesSinPausa = viajeFeign.reporteMonopatinesSinPausa();
            log.info(monopatinesSinPausa.toString());
            return new ArrayList<>(monopatinesSinPausa);
        }
    }

}
