package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Cliente;
import org.gestion.proyecto_gasolinera.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.gestion.proyecto_gasolinera.repositories.ClienteRepository;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    ClienteService clienteService;

    @GetMapping("/test")
    public String test() {
        return "FUNCIONA";
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> index() {
        return ResponseEntity.ok(this.clienteService.verClientes());
    }

    @PostMapping
    public ResponseEntity<Cliente> create(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.guardarCliente(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }
}