package org.gestion.proyecto_gasolinera.controllers;

import org.gestion.proyecto_gasolinera.Cliente;
import org.gestion.proyecto_gasolinera.service.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class LoginController {
    private final ClienteService clienteService;

    public LoginController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Cliente datos) {
        Cliente cliente = clienteService.login(datos.getUsername(), datos.getPass());
        if (cliente == null) {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
        return ResponseEntity.ok(cliente);
    }
}
