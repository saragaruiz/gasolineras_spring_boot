package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.repositories.ClienteRepository;
import org.gestion.proyecto_gasolinera.repositories.RegistrosRepository;
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
    private final RegistrosRepository registrosRepository;

    public WebController(ClienteService clienteService,  ClienteRepository clienteRepository, GasolineraService gasolineraService, RegistrosRepository registrosRepository) {
        this.clienteService = clienteService;
        this.clienteRepository = clienteRepository;
        this.gasolineraService = gasolineraService;
        this.registrosRepository = registrosRepository;
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
            if(cliente.isAdmin()){
                return "redirect:/clientes/web";
            }else{
                return "redirect:/clientes/perfil/" + cliente.getClientId();
            }
        }
            return "login";
    }
    @GetMapping("/clientes/perfil/{id}")
    public String verPerfil(@PathVariable int id, Model model) {
        Cliente cliente = clienteService.findById(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("gasolineras", gasolineraService.verGasolineras());
        return "vistaCliente";
    }

    @GetMapping("/clientes/web")
    public String listar(@RequestParam(required = false) Boolean soloActivos,@RequestParam(required = false) Long editingId, Model model) {
        if (Boolean.TRUE.equals(soloActivos)) {
            model.addAttribute("clientes", clienteService.verClientesActivos());
        } else {
            model.addAttribute("clientes", clienteService.verClientes());
        }
        model.addAttribute("gasolineras", gasolineraService.verGasolineras());
        model.addAttribute("editingId", editingId);
        model.addAttribute("soloActivos", soloActivos);

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
        cliente.setIsAdmin(false);
        cliente.setActive(true);
        clienteService.guardarCliente(cliente);
        clienteService.guardarRegistro(cliente, "Alta de cliente");
        return "redirect:/clientes/web";
    }
    @GetMapping("/clientes/eliminar/{id}")
    public String eliminarCliente(@PathVariable int id){
        Cliente cliente = clienteService.findById(id);
        clienteService.guardarRegistro(clienteService.findById(id), "Cliente eliminado");
        clienteService.borrarCliente(id);
        return "redirect:/clientes/web";
    }
    @GetMapping("/clientes/admin/{id}")
    public String asignarAdmin(@PathVariable int id){
        clienteService.asignarAdmin(id);
        clienteService.guardarRegistro(clienteService.findById(id), "Asignado como Admin");
        return "redirect:/clientes/web";
    }
   @GetMapping("/clientes/quitar-admin/{id}")
    public String quitarAdmin(@PathVariable int id){
        clienteService.quitarAdmin(id);
       clienteService.guardarRegistro(clienteService.findById(id), "Quitado de Admin");
        return "redirect:/clientes/web";
    }

    @GetMapping("/clientes/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("clientes", clienteService.verClientes());
        model.addAttribute("editingId", id);
        return "clientes";
    }
    @GetMapping("/clientes/editar-perfil/{id}")
    public String editarPerfil(@PathVariable int id, Model model) {
        model.addAttribute("cliente", clienteService.findById(id));
        model.addAttribute("gasolineras", gasolineraService.verGasolineras());
        model.addAttribute("editingId", id);
        return "vistaCliente";
    }

    @PostMapping("/clientes/guardar")
    public String guardar(@RequestParam int id, @RequestParam String name,  @RequestParam String surname,  @RequestParam String username, @RequestParam String nif, @RequestParam String active, @RequestParam(required = false)Integer gasStationId, Model model) {
        Cliente cliente = clienteService.findById(id);
        cliente.setName(name);
        cliente.setSurname(surname);
        cliente.setUsername(username);
        cliente.setNif(nif);
        cliente.setActive(Boolean.parseBoolean(active));
        if (gasStationId != null) {
            Gasolinera g = gasolineraService.findById(gasStationId);
            cliente.setGasStation(g);
        } else {
            cliente.setGasStation(null);
        }
        clienteService.actualizarCliente(cliente);
        clienteService.guardarRegistro(cliente, "Perfil editado");
        return "redirect:/clientes/perfil/" + id;
    }

    @PostMapping("/clientes/asignar-favorita")
    public String asignarFavorita(@RequestParam int clientId, @RequestParam(required = false) Integer gasStationId){
        if(gasStationId == null){
            return "redirect:/clientes/web";
        }
        Cliente c = clienteService.findById(clientId);
        Gasolinera g = gasolineraService.findById(gasStationId);
        c.setGasStation(g);
        clienteService.actualizarCliente(c);
        clienteService.guardarRegistro(c, "Gasolinera marcada como favorita");
        return "redirect:/clientes/web";
    }
    @GetMapping("/clientes/cambiar-password")
    public String cambiarPassword(){
        return "CambiarContrasenia";
    }
    @PostMapping("/clientes/cambiar-password")
    public String cambiarPassword(@RequestParam String username,
                                  @RequestParam String nuevaPass) {

        clienteService.cambiarContraseña(username, nuevaPass);
        Cliente cliente= clienteService.findByUsername(username);
        clienteService.guardarRegistro(cliente, "Contraseña cambiada");
        return "redirect:/clientes/web";
    }
    @GetMapping("/clientes/baja/{id}")
    public String darBaja(@PathVariable int id) {
        Cliente cliente = clienteService.findById(id);
        cliente.setActive(false);
        clienteService.actualizarCliente(cliente);
        clienteService.guardarRegistro(cliente, "Baja");
        return "redirect:/clientes/web";
    }
    @PostMapping("/clientes/crear-cuenta")
    public String crearCuenta(@RequestParam String name,
                              @RequestParam String surname,
                              @RequestParam String username,
                              @RequestParam String nif,
                              @RequestParam String pass) {
        Cliente cliente = new Cliente();
        cliente.setName(name);
        cliente.setSurname(surname);
        cliente.setUsername(username);
        cliente.setNif(nif);
        cliente.setPass(pass);
        cliente.setActive(true);
        cliente.setIsAdmin(false);
        clienteService.guardarCliente(cliente);
        return "crearCuenta";
    }
    @GetMapping("/clientes/crear-cuenta")
    public String verCrearCuenta() {
        return "crearCuenta";
    }
    @GetMapping({"/clientes/registros", "/clientes/registros/"})
    public String verRegistros(@RequestParam(required = false) String buscar, Model model) {
        if (buscar != null && !buscar.isBlank()) {
            model.addAttribute("registros", clienteService.buscarRegistrosPorCliente(buscar));
        } else {
            model.addAttribute("registros", clienteService.verTodosRegistros());
        }
        model.addAttribute("buscar", buscar);
        return "registros";
    }


}

