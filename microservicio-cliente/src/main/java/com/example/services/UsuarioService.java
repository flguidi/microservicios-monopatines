package com.example.services;

import com.example.dto.UsuarioDto;
import com.example.entities.Usuario;
import com.example.feignClients.MonopatinFeign;
import com.example.mappers.UsuarioMapper;
import com.example.model.Monopatin;
import com.example.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UsuarioMapper usuarioMapper;
    @Autowired
    private MonopatinFeign monopatinFeign;


    @Transactional(readOnly = true)
    public List<UsuarioDto> findAll() {
        return this.usuarioRepository.findAll()
                .stream().map(UsuarioDto::new).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioDto findById(Long id) {
        return this.usuarioRepository.findById(id)
                .map(UsuarioDto::new)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado."));
    }

    @Transactional
    public UsuarioDto save(UsuarioDto usuarioDto) {
        if (usuarioDto.getNombreUsuario().isEmpty() ||
                usuarioDto.getNombre().isEmpty() ||
                usuarioDto.getApellido().isEmpty()) {
            throw new RuntimeException("Todos los campos son requeridos para crear un usuario.");
        }

        Usuario nuevoUsuario = new Usuario(
                usuarioDto.getNombreUsuario(),
                usuarioDto.getNombre(),
                usuarioDto.getApellido(),
                usuarioDto.getNroCelular(),
                usuarioDto.getEmail()
        );

        // Guardar el usuario en base de datos
        Usuario guardado = usuarioRepository.save(nuevoUsuario);

        // Retornar UsuarioDto
        return usuarioMapper.toUsuarioDto(guardado);
    }

    @Transactional
    public UsuarioDto update(Long id, UsuarioDto usuarioDto) {
        if (usuarioDto.getNombreUsuario().isEmpty() ||
                usuarioDto.getNombre().isEmpty() ||
                usuarioDto.getApellido().isEmpty()) {
            throw new RuntimeException("Todos los campos son requeridos para crear un usuario.");
        }

        // Buscar el usuario por ID
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));

        // Actualizar los campos del usuario con los datos del DTO
        usuarioExistente.setNombreUsuario(usuarioDto.getNombreUsuario());
        usuarioExistente.setNombre(usuarioDto.getNombre());
        usuarioExistente.setApellido(usuarioDto.getApellido());

        // Guardar los cambios
        usuarioRepository.save(usuarioExistente);

        return usuarioMapper.toUsuarioDto(usuarioExistente);
    }

    @Transactional
    public UsuarioDto delete(Long id) {
        Usuario usuarioEliminado = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));

        // Eliminar el usuario
        usuarioRepository.delete(usuarioEliminado);

        // Devolver el usuario eliminado
        return usuarioMapper.toUsuarioDto(usuarioEliminado);
    }

    // 3. g) Cantidad de monopatines disponibles en una zona especificada.
    @Transactional(readOnly = true)
    public List<Monopatin> obtenerMonopatinesCercanos(Double latitud, Double longitud) {
        try {
            return monopatinFeign.obtenerMonopatinesCercanos(latitud, longitud);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al obtener monopatines cercanos.");
        }
    }

}
