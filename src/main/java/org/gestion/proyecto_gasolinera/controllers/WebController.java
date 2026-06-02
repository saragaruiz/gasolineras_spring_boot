package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.repositories.ClienteRepository;
import org.gestion.proyecto_gasolinera.service.GasolineraService;
import org.springframework.ui.Model;
import org.gestion.proyecto_gasolinera.Cliente;
import org.gestion.proyecto_gasolinera.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
    private final ClienteService clienteService;
    private final ClienteRepository clienteRepository;
    private final GasolineraService gasolineraService;

    public WebController(ClienteService clienteService,  ClienteRepository clienteRepository, GasolineraService gasolineraService) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
        this.gasolineraService = gasolineraService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/clientes/login-web")
    public String loginWeb(@RequestParam String username,
                           @RequestParam String pass,
                           Model model) {
        Cliente cliente = clienteService.login(username, pass);
        if(cliente != null){
            model.addAttribute("cliente", cliente);
            model.addAttribute("cliente", clienteService.verClientes());
        return "redirect:/clientes/web";
        }
            return "login";
    }

    @GetMapping("/clientes/web")
    public String listar(@RequestParam(required = false) Long editingId, Model model) {

        model.addAttribute("clientes", clienteService.verClientes());
        model.addAttribute("gasolineras", gasolineraService.verGasolineras());
        model.addAttribute("editingId", editingId);

        return "clientes";
    }
    @GetMapping("/clientes/nuevo")
    public String nuevoCliente(){
        return "nuevoCliente";
    }
    @PostMapping("/clientes/guardar-web")
    public String guardarCliente(@RequestParam String name, @RequestParam String surname, @RequestParam String username, @RequestParam String nif, @RequestParam String pass){
        Cliente cliente = new Cliente();
        cliente.setName(name);
        cliente.setSurname(surname);
        cliente.setUsername(username);
        cliente.setNif(nif);
        cliente.setPass(pass);
        clienteService.guardarCliente(cliente);
        return "redirect:/clientes/web";
    }
    @GetMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable int id){
        clienteService.borrarCliente(id);
        return "redirect:/clientes/web";
    }
    @GetMapping("/clientes/admin/{id}")
    public String asignarAdmin(@PathVariable int id){
        clienteService.asignarAdmin(id);
        return "redirect:/clientes/web";
    }
   @GetMapping("/clientes/quitar-admin/{id}")
    public String quitarAdmin(@PathVariable int id){
        clienteService.quitarAdmin(id);
        return "redirect:/clientes/web";
    }

    @GetMapping("/clientes/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("clientes", clienteService.verClientes());
        model.addAttribute("editingId", id);
        return "clientes";
    }

    @PostMapping("/clientes/guardar")
    public String guardar(@RequestParam int id, @RequestParam String name, @RequestParam String surname,  @RequestParam String username, @RequestParam String nif, @RequestParam boolean active, @RequestParam(required = false)Integer gasStationId){
        Cliente cliente = clienteService.findById(id);
        cliente.setName(name);
        cliente.setSurname(surname);
        cliente.setUsername(username);
        cliente.setNif(nif);
        cliente.setActive(active);
        if (gasStationId != null) {
            Gasolinera g = gasolineraService.findById(gasStationId);
            cliente.setGasStation(g);
        } else {
            cliente.setGasStation(null);
        }
        clienteService.guardarCliente(cliente);

        return "redirect:/clientes/web";
    }
    @PostMapping("/clientes/asignar-favorita")
    public String asignarFavorita(@RequestParam int clientId, @RequestParam(required = false) Integer gasStationId){
        if(gasStationId == null){
            return "redirect:/clientes/web";
        }
        Cliente c = clienteService.findById(clientId);
        Gasolinera g = gasolineraService.findById(gasStationId);
        c.setGasStation(g);
        clienteService.guardarCliente(c);
        return "redirect:/clientes/web";
    }
}
