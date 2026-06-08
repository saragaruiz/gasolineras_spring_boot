package org.gestion.proyecto_gasolinera.repositories;

import org.gestion.proyecto_gasolinera.Registros;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrosRepository extends JpaRepository<Registros, Integer> {
    Page<Registros> findAll(Pageable pageable);
    Page<Registros> findByClienteNameContainingIgnoreCase(String name, Pageable pageable);
}
