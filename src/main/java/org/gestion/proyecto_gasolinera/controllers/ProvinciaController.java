package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Provincia;
import org.gestion.proyecto_gasolinera.service.ProvinciaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provincias")
public class ProvinciaController {
    private final ProvinciaService provinciaService;
    public ProvinciaController(ProvinciaService provinciaService){
        this.provinciaService = provinciaService;
    }
    @GetMapping
    public List<Provincia> getAll(){
        return provinciaService.verProvincias();
    }
    @PostMapping
    public Provincia crear(@RequestBody Provincia provincia){
        return provinciaService.guardarProvincia(provincia);
    }
    @DeleteMapping("/{id}")
    public void borrar(@PathVariable int id){
        provinciaService.borrarProvincia(id);
    }
}
