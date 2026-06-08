package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.Registros;
import org.gestion.proyecto_gasolinera.service.GasolineraService;
import org.springframework.ui.Model;
import org.gestion.proyecto_gasolinera.Cliente;
import org.gestion.proyecto_gasolinera.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;


@Controller
public class WebController {
    private final ClienteService clienteService;
    private final GasolineraService gasolineraService;

    public WebController(ClienteService clienteService,  GasolineraService gasolineraService) {
        this.clienteService = clienteService;
        this.gasolineraService = gasolineraService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/clientes/login-web")
    public String loginWeb(@RequestParam String username,
                           @RequestParam String pass) {

        Cliente cliente = clienteService.login(username, pass);
        if(cliente != null){
            if(cliente.isAdmin()){
                return "redirect:/clientes/web";
            }else{
                return "redirect:/clientes/perfil/" + cliente.getClientId();
            }
        }
        return "redirect:/login?error=true";

    }

    @GetMapping("/clientes/perfil/{id}")
    public String verPerfil(@PathVariable int id, Model model) {
        Cliente cliente = clienteService.findById(id);
        model.addAttribute("cliente", cliente);
        model.addAttribute("gasolineras", gasolineraService.verGasolineras());
        return "vistaCliente";
    }

    @GetMapping("/clientes/web")
    public String listar(@RequestParam(required = false) Boolean soloActivos,@RequestParam(required = false) Long editingId, @RequestParam(defaultValue = "0") int pagina, @RequestParam(required = false) String buscar, Model model) {
        Page<Cliente> paginaClientes;

        if (buscar != null && !buscar.isBlank()) {
            paginaClientes = clienteService.buscarClientes(buscar, pagina);
        } else if (Boolean.TRUE.equals(soloActivos)) {
            paginaClientes = clienteService.verClientesActivos(pagina);
        } else {
            paginaClientes = clienteService.verClientes(pagina);
        }
        model.addAttribute("clientes", paginaClientes.getContent());
        model.addAttribute("totalPaginas", paginaClientes.getTotalPages());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("buscar", buscar);
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
        Page<Cliente> paginaClientes = clienteService.verClientes(0);
        model.addAttribute("clientes", paginaClientes.getContent());
        model.addAttribute("totalPaginas", paginaClientes.getTotalPages());
        model.addAttribute("paginaActual", 0);
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
    public String guardar(@RequestParam int id, @RequestParam String name,  @RequestParam String surname,  @RequestParam String username, @RequestParam String nif, @RequestParam String active, @RequestParam(required = false)Integer gasStationId,  @RequestParam(required = false) String origen) {
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
        if("admin".equals(origen)){
            return "redirect:/clientes/web";
        }else {
            return "redirect:/clientes/perfil/" + id;
        }
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
    public String cambiarPassword(@RequestParam(required = false) String origen,
                                  @RequestParam(required = false) Integer clienteId,
                                  Model model) {
        model.addAttribute("origen", origen);
        model.addAttribute("clienteId", clienteId);
        return "CambiarContrasenia";
    }
    @PostMapping("/clientes/cambiar-password")
    public String cambiarPassword(@RequestParam String username, @RequestParam String nuevaPass,  @RequestParam(required = false) String origen,  @RequestParam(required = false) Integer clienteId) {

        clienteService.cambiarContraseña(username, nuevaPass);
        Cliente cliente= clienteService.findByUsername(username);
        clienteService.guardarRegistro(cliente, "Contraseña cambiada");
        if ("perfil".equals(origen) && clienteId != null) {
            return "redirect:/clientes/perfil/" + clienteId;
        }
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
    public String verRegistros(@RequestParam(required = false) String buscar, @RequestParam(defaultValue = "0") int pagina, Model model) {
        Page<Registros> paginaRegistros;

        if (buscar != null && !buscar.isBlank()) {
            paginaRegistros = clienteService.buscarRegistrosPorCliente(buscar, pagina);
        } else {
            paginaRegistros = clienteService.verTodosRegistros(pagina);
        }
        model.addAttribute("registros", paginaRegistros.getContent());
        model.addAttribute("totalPaginas", paginaRegistros.getTotalPages());
        model.addAttribute("paginaActual", pagina);
        model.addAttribute("buscar", buscar);
        return "registros";
    }


}

