package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.Provincia;
import org.gestion.proyecto_gasolinera.service.ProvinciaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/provincias")
public class ProvinciaController {
    private final ProvinciaService provinciaService;

    public ProvinciaController(ProvinciaService provinciaService){

        this.provinciaService = provinciaService;
    }
    @GetMapping("/web")
    public String listar(@RequestParam(required = false) Long editingId, Model model) {

        model.addAttribute("provincias", provinciaService.verProvincias());
        model.addAttribute("editingId", editingId);

        return "provincias";
    }
    @GetMapping("/nuevo")
    public String nuevaProvincia(){
        return "nuevaProvincia";
    }
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Provincia p) {
        provinciaService.guardarProvincia(p);
        return "redirect:/provincias/web";
    }


    @GetMapping("/eliminar{id}")
    public String eliminarProvincia(@PathVariable int id){
        provinciaService.borrarProvincia(id);
        return "redirect:/provincias/web";
    }
}
