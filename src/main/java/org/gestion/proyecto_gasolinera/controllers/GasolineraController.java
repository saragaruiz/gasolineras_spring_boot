package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.Provincia;
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

        model.addAttribute("gasolinera", gasolineraService.verGasolineras());
        model.addAttribute("editingId", editingId);

        return "gasolinera";
    }

    @GetMapping("/nuevo")
    public String nuevaGasolinera(Model model) {
        model.addAttribute("gasolinera", new Gasolinera());
        model.addAttribute("provincias", provinciaService.verProvincias());
        return "nuevaGasolinera";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Gasolinera g,
                          @RequestParam int provinceId) {

        Provincia p = provinciaService.findById(provinceId);

        g.setProvince(p);
        g.setActive("true");

        gasolineraService.guardarGasolinera(g);
        return "redirect:/gasolineras/web";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable int id) {
        gasolineraService.borrarGasolinera(id);
        return "redirect:/gasolineras/web";
    }
}

