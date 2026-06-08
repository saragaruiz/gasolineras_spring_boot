package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Provincia;
import org.gestion.proyecto_gasolinera.repositories.ProvinciaRepository;
import org.gestion.proyecto_gasolinera.service.ProvinciaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@Controller
@RequestMapping("/provincias")
public class ProvinciaController {
    private final ProvinciaService provinciaService;
    private final ProvinciaRepository provinciaRepository;

    public ProvinciaController(ProvinciaService provinciaService, ProvinciaRepository provinciaRepository){
        this.provinciaService = provinciaService;
        this.provinciaRepository = provinciaRepository;
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


    @GetMapping("/eliminar/{id}")
    public String eliminarProvincia(@PathVariable int id){
        provinciaService.borrarProvincia(id);
        return "redirect:/provincias/web";
    }
    @GetMapping("/provincia/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("provincias", provinciaService.verProvincias());
        model.addAttribute("editingId", id);
        return "provincias";
    }
    @PostMapping("/crear")
    public String crear(@RequestParam int provinceId,
                        @RequestParam String name,
                        @RequestParam String creationDate) {

        if (provinciaRepository.existsById(provinceId)) {
            return "redirect:/provincias/web";
        }

        Provincia p = new Provincia();
        p.setProvinceId(provinceId);
        p.setName(name);
        p.setCreationDate(LocalDate.parse(creationDate));

        provinciaRepository.save(p);

        return "redirect:/provincias/web";
    }
}
