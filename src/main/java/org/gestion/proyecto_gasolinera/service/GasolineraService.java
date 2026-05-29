package org.gestion.proyecto_gasolinera.service;

import org.gestion.proyecto_gasolinera.DTO.GasolineraProvinciaDTO;
import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.repositories.GasolineraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GasolineraService {
    @Autowired
    GasolineraRepository gasolineraRepository;

    //guardar gasolinera
    public Gasolinera guardarGasolinera(Gasolinera gasolinera){
        return gasolineraRepository.save(gasolinera);
    }
    public List<Gasolinera> verGasolineras(){return gasolineraRepository.findAll();}

    public Gasolinera actualizarGasolinera(Gasolinera gasolinera){
        return gasolineraRepository.save(gasolinera);
    }

    public void borrarGasolinera(int id){
        gasolineraRepository.deleteById(id);
    }
    public List<GasolineraProvinciaDTO> buscarPorProvincia(String provincia){
        return gasolineraRepository.buscarPorProvincia(provincia);
    }

}
