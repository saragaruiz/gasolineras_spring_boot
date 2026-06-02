package org.gestion.proyecto_gasolinera.service;

import org.gestion.proyecto_gasolinera.DTO.GasolineraProvinciaDTO;
import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.repositories.GasolineraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GasolineraService {
    GasolineraRepository gasolineraRepository;
    public GasolineraService(GasolineraRepository gasolineraRepository){
        this.gasolineraRepository = gasolineraRepository;
    }

    public List<Gasolinera> verGasolineras(){
        return gasolineraRepository.findAll();
    }

    public Gasolinera findById(int id) {
        return gasolineraRepository.findById(id).orElse(null);
    }

    public Gasolinera guardarGasolinera(Gasolinera g){
        return gasolineraRepository.save(g);
    }

    public void borrarGasolinera(int id){

        gasolineraRepository.deleteById(id);
    }
    public List<GasolineraProvinciaDTO> buscarPorProvincia(String provincia){
        return gasolineraRepository.buscarPorProvincia(provincia);
    }

}
