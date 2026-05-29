package org.gestion.proyecto_gasolinera.service;

import org.gestion.proyecto_gasolinera.Provincia;
import org.gestion.proyecto_gasolinera.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProvinciaService {
    @Autowired
    ProvinciaRepository provinciaRepository;
    public Provincia guardarProvincia(Provincia provincia){
        return provinciaRepository.save(provincia);
    }
    public List<Provincia> verProvincias(){
        return provinciaRepository.findAll();
    }
    public void borrarProvincia(int id) {
        if (provinciaRepository.existsById(id)) {
            provinciaRepository.deleteById(id);
        }
    }
}
