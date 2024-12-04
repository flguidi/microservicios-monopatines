package com.example.services;

import com.example.dto.ParadaDto;
import com.example.entities.Parada;
import com.example.mappers.ParadaMapper;
import com.example.repository.ParadaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParadaService {

    @Autowired
    ParadaRepository paradaRepository;

    @Autowired
    ParadaMapper paradaMapper;

    public List<ParadaDto> findAll() {
        return this.paradaRepository.findAll()
                .stream().map(paradaMapper::toParadaDto).toList();
    }

    public ParadaDto findById(String id) {
        return this.paradaRepository.findById(id)
                .map(paradaMapper::toParadaDto)
                .orElseThrow(() -> new RuntimeException("Parada con ID " + id + " no encontrado"));
    }

    public ParadaDto save(ParadaDto paradaDto) {
        if (paradaDto.getNombre().isEmpty() || paradaDto.getLatitud() == 0.0  || paradaDto.getLongitud() == 0.0 || paradaDto.getCantMaxMonopatin() == null ) {
            throw new RuntimeException("Todos los campos son requeridos para crear una parada.");
        }

        // Convertir el DTO a entidad Parada
        Parada nuevaParada = paradaMapper.toParada(paradaDto);

        // Guardar la parada en la base de datos
        Parada guardada = paradaRepository.save(nuevaParada);

        return paradaMapper.toParadaDto(guardada);
    }

    public ParadaDto update(String id, ParadaDto paradaDto) {
        if (paradaDto.getNombre().isEmpty() || paradaDto.getLatitud() == 0.0  || paradaDto.getLongitud() == 0.0 || paradaDto.getCantMaxMonopatin() == null ) {
            throw new RuntimeException("Todos los campos son requeridos para modificar una parada.");
        }

        // Buscar la parada por ID
        Parada paradaExistente = paradaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parada con ID " + id + " no encontrado"));

        // Actualizar los campos de la parada con los datos del DTO
        paradaExistente.setNombre(paradaDto.getNombre());
        paradaExistente.setLatitud(paradaDto.getLatitud());
        paradaExistente.setCantMaxMonopatin(paradaDto.getCantMaxMonopatin());

        // Guardar los cambios
        paradaRepository.save(paradaExistente);

        return paradaMapper.toParadaDto(paradaExistente);
    }

    public ParadaDto delete(String id) {
        Parada paradaExistente = paradaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Parada con ID " + id + " no encontrado"));

        // Eliminar la parada
        paradaRepository.delete(paradaExistente);

        // Devolver la parada eliminada
        return paradaMapper.toParadaDto(paradaExistente);
    }
}
