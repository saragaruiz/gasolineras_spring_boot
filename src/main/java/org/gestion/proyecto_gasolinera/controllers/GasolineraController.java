package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.service.GasolineraService;
import org.gestion.proyecto_gasolinera.service.ProvinciaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/gasolineras")
public class GasolineraController {
    private final GasolineraService gasolineraService;
    private final ProvinciaService provinciaService;

    public GasolineraController(GasolineraService gasolineraService, ProvinciaService provinciaService) {
        this.gasolineraService = gasolineraService;
        this.provinciaService = provinciaService;
    }

    @GetMapping("/web")
    public String listar(@RequestParam(required = false) Long editingId, Model model) {

        model.addAttribute("gasolineras", gasolineraService.verGasolineras());
        model.addAttribute("provincias", provinciaService.verProvincias());
        model.addAttribute("editingId", editingId);

        return "gasolineras";
    }

    @GetMapping("/nuevo")
    public String nuevaGasolinera(Model model) {
        model.addAttribute("gasolineras", new Gasolinera());
        model.addAttribute("provincias", provinciaService.verProvincias());
        return "nuevaGasolinera";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Integer gasStationId, @RequestParam String name, @RequestParam(required = false) String active, @RequestParam double petrolPrice, @RequestParam(required = false) Integer provinceId) {
        Gasolinera gasolinera;
        if (gasStationId != null) {
            gasolinera = gasolineraService.findById(gasStationId);
        } else {
            gasolinera = new Gasolinera();
        }
        gasolinera.setName(name);
        gasolinera.setActive(active);
        gasolinera.setPetrolPrice(petrolPrice);
        if (provinceId != null) {
            gasolinera.setProvince(provinciaService.findById(provinceId));
        } else {
            gasolinera.setProvince(null);
        }
        gasolineraService.guardarGasolinera(gasolinera);

        return "redirect:/gasolineras/web";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        gasolineraService.borrarGasolinera(id);
        return "redirect:/gasolineras/web";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable int id, Model model) {
        model.addAttribute("gasolineras", gasolineraService.verGasolineras());
        model.addAttribute("provincias", provinciaService.verProvincias());
        model.addAttribute("editingId", id);
        return "gasolineras";
    }

}

