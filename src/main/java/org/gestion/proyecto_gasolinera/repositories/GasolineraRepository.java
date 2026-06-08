package org.gestion.proyecto_gasolinera.repositories;

import org.gestion.proyecto_gasolinera.DTO.GasolineraProvinciaDTO;
import org.gestion.proyecto_gasolinera.Gasolinera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GasolineraRepository extends JpaRepository<Gasolinera, Integer> {

    @Query(""" 

            SELECT new org.gestion.proyecto_gasolinera.DTO.GasolineraProvinciaDTO(g.gasStationId, g.name, p.name, g.active)
         FROM Gasolinera g JOIN g.province p
         WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :provincia, '%'))""")
    List<GasolineraProvinciaDTO> buscarPorProvincia(@Param("provincia") String provincia);
    List<Gasolinera> findByNameContainingIgnoreCase(String name);
}

