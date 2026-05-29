package org.gestion.proyecto_gasolinera.repositories;

import org.gestion.proyecto_gasolinera.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    Cliente findByUsername(String username);
}
