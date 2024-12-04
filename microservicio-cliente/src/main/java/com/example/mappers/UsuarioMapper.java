package com.example.mappers;

import com.example.dto.UsuarioDto;
import com.example.entities.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    // Convierte una usuario a usuarioDto
    UsuarioDto toUsuarioDto(Usuario usuario);

    // Convierte un usuarioDto a usuario
    Usuario toUsuario(UsuarioDto usuarioDto);

}
