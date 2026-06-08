package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Cliente;
import org.gestion.proyecto_gasolinera.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> index() {
        return ResponseEntity.ok(clienteService.verTodosClientes());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody Cliente cliente) {
        if(cliente.getUsername() == null || cliente.getPass() == null){
            return ResponseEntity.badRequest().body("Hay campos vacíos");
        }
        Cliente logueado = clienteService.login(cliente.getUsername(), cliente.getPass());
        if(logueado == null){
            return ResponseEntity.status(401).body("Usuario o contraeña incorrecto");
        }
        return ResponseEntity.ok(logueado);
    }
}