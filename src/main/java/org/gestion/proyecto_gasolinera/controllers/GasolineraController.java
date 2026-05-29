package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.DTO.GasolineraProvinciaDTO;
import org.gestion.proyecto_gasolinera.service.GasolineraService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/gasolineras")
public class GasolineraController {
    private final GasolineraService gasolineraService;

    public GasolineraController(GasolineraService gasolineraService) {
        this.gasolineraService = gasolineraService;
    }

    @GetMapping
    public List<GasolineraProvinciaDTO> buscarPorProvincia(@RequestParam String provincia){
        return gasolineraService.buscarPorProvincia(provincia);
    }
}
