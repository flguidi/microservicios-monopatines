package com.example.services;

import com.example.dto.MonopatinDto;
import com.example.entities.Monopatin;
import com.example.mappers.MonopatinMapper;
import com.example.repository.MonopatinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonopatinService {

    @Autowired
    private final MonopatinRepository monopatinRepository;
    @Autowired
    private final MonopatinMapper monopatinMapper;

    @Transactional(readOnly = true)
    public List<MonopatinDto> findAll() {
        return this.monopatinRepository.findAll()
                .stream()
                .map(MonopatinDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public MonopatinDto findById(Long id) {
        return monopatinRepository.findById(id)
                .map(MonopatinDto::new)
                .orElseThrow(() -> new RuntimeException("Monopatín con ID " + id + " no encontrado."));
    }

    @Transactional
    public MonopatinDto save(MonopatinDto monopatinDto) {
        if (monopatinDto.getEstado().isEmpty() ||
                monopatinDto.getLatitud() == null ||
                monopatinDto.getLongitud() == null ||
                monopatinDto.getKmRecorridos() == null ||
                monopatinDto.getTiempoUso() == null) {

            throw new RuntimeException("Todos los campos son requeridos para crear un monopatín.");
        }

        Monopatin nuevoMonopatin = monopatinMapper.toMonopatin(monopatinDto);
        Monopatin guardado = monopatinRepository.save(nuevoMonopatin);
        return monopatinMapper.toMonopatinDto(guardado);
    }


    @Transactional
    public MonopatinDto update(Long id, MonopatinDto monopatinDto) {
        Monopatin monopatinExistente = monopatinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Monopatín con ID " + id + " no encontrado."));

        monopatinExistente.setEstado(monopatinDto.getEstado());
        monopatinExistente.setLatitud(monopatinDto.getLatitud());
        monopatinExistente.setLongitud(monopatinDto.getLongitud());
        monopatinExistente.setKmRecorridos(monopatinDto.getKmRecorridos());
        monopatinExistente.setTiempoUso(monopatinDto.getTiempoUso());

        Monopatin actualizado = monopatinRepository.save(monopatinExistente);
        return monopatinMapper.toMonopatinDto(actualizado);
    }

    @Transactional
    public MonopatinDto delete(Long id) {
        Monopatin monopatinExistente = monopatinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Monopatín con ID " + id + " no encontrado."));

        monopatinRepository.delete(monopatinExistente);
        return monopatinMapper.toMonopatinDto(monopatinExistente);
    }

    // ---------------------------------------- Servicios ----------------------------------------

    @Transactional
    public void establecerEstado(Long id, String estado) {
        Monopatin monopatinExistente = monopatinRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Monopatín con ID " + id + " no encontrado."));

        monopatinExistente.setEstado(estado);
    }

    // 3. e) Devolver cantidad de monopatines según su estado.
    public int obtenerCantidadEnOperacion() {
        return monopatinRepository.countByEstado("En uso") + monopatinRepository.countByEstado("En parada");
    }

    public int obtenerCantidadEnMantenimiento() {
        return monopatinRepository.countByEstado("En mantenimiento");
    }

    // 3. g) Obtener cantidad de monopatines disponibles en una zona especificada.
    public List<MonopatinDto> obtenerMonopatinesCercanos(double latitud, double longitud) {
        double rango = 1000;
        List<Monopatin> monopatines = monopatinRepository.findMonopatinesCercanos(latitud, longitud, rango);

        return monopatines.stream()
                .map(monopatin -> new MonopatinDto(
                        monopatin.getEstado(),
                        monopatin.getLatitud(),
                        monopatin.getLongitud(),
                        monopatin.getKmRecorridos(),
                        monopatin.getTiempoUso()
                ))
                .collect(Collectors.toList());
    }

}