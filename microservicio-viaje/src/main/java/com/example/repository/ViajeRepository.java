package com.example.repository;

import com.example.dto.CantViajesMonopatinDto;
import com.example.dto.MonopatinConPausaDto;
import com.example.dto.MonopatinSinPausaDto;
import com.example.entities.Viaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViajeRepository extends JpaRepository<Viaje,Long> {

    @Query("SELECT new com.example.dto.MonopatinSinPausaDto(v.idMonopatin, SUM(v.kmRecorridos)) " +
            "FROM Viaje v " +
            "GROUP BY v.idMonopatin")
    List<MonopatinSinPausaDto> getMonopatinesSinPausa();

    @Query("SELECT new com.example.dto.MonopatinConPausaDto(v.idMonopatin, SUM(v.kmRecorridos), SUM(v.minPausa)) " +
            "FROM Viaje v " +
            "GROUP BY v.idMonopatin")
    List<MonopatinConPausaDto> getMonopatinesConPausa();

    @Query("SELECT new com.example.dto.CantViajesMonopatinDto(v.idMonopatin, COUNT(v)) " +
            "FROM Viaje v " +
            "WHERE YEAR(v.inicio) = :anio " +
            "GROUP BY v.idMonopatin " +
            "HAVING COUNT(v) > :cantViajes")
    List<CantViajesMonopatinDto> monopatinesCantViajesAnio(@Param("cantViajes") Integer cantViajes, @Param("anio") Integer anio);

    @Query("SELECT v FROM Viaje v " +
            "WHERE YEAR(v.inicio) = :anio " +
            "AND MONTH(v.inicio) BETWEEN :mesInicio AND :mesFin")
    List<Viaje> obtenerViajesEnRango(@Param("anio") int anio, @Param("mesInicio") int mesInicio, @Param("mesFin") int mesFin);

}
