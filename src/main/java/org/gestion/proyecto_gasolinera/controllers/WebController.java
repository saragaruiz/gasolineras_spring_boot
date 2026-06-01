package org.gestion.proyecto_gasolinera.controllers;

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

    public WebController(ClienteService clienteService) {
        this.clienteService = clienteService;
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
    public String clientesWeb(Model model){
        model.addAttribute("clientes", clienteService.verClientes());
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
}
