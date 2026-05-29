package org.gestion.proyecto_gasolinera.repositories;

import org.gestion.proyecto_gasolinera.Registros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrosRepository extends JpaRepository<Registros, Integer> {
}
