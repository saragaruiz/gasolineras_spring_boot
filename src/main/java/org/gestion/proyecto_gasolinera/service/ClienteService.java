package org.gestion.proyecto_gasolinera.service;

import org.gestion.proyecto_gasolinera.Cliente;
import org.gestion.proyecto_gasolinera.Registros;
import org.gestion.proyecto_gasolinera.repositories.ClienteRepository;
import org.gestion.proyecto_gasolinera.repositories.RegistrosRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final RegistrosRepository registrosRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteService(ClienteRepository clienteRepository, RegistrosRepository registrosRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.registrosRepository = registrosRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Guardar cliente
    public Cliente guardarCliente(Cliente cliente) {
        cliente.setPass(passwordEncoder.encode(cliente.getPass()));
        return clienteRepository.save(cliente);
    }

    //ver clientes
    public Page<Cliente> verClientes(int pagina) {
        Pageable pageable = PageRequest.of(pagina, 6);
        return clienteRepository.findAll(pageable);
    }
    //Actualizar cliente
    public Cliente actualizarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public void borrarCliente(int id) {
        clienteRepository.deleteById(id);
    }

    public Cliente findById(int id) {
        return clienteRepository.findById(id)
                .orElse(null);
    }

    //Login
    public Cliente login(String username, String pass) {
        Cliente cliente = clienteRepository.findByUsername(username);
        if (cliente == null) return null;
        if (cliente.getPass() == null) return null;
        if (passwordEncoder.matches(pass, cliente.getPass())) return cliente;
        return null;
    }

    //cambiar contraseña
    public Cliente cambiarContraseña(String username, String nuevaPass) {
        Cliente cliente = clienteRepository.findByUsername(username);
        if(cliente == null){
            throw new RuntimeException("Usuario no encontrado");
        }
        cliente.setPass(passwordEncoder.encode(nuevaPass));
        return clienteRepository.save(cliente);
    }

    //asignar admin
    public Cliente asignarAdmin(int clientId) {
        Cliente cliente = clienteRepository.findById(clientId).orElseThrow();
        cliente.setIsAdmin(true);
        return clienteRepository.save(cliente);
    }

    public Cliente quitarAdmin(int clientId) {
        Cliente cliente = clienteRepository.findById(clientId).orElseThrow();
        cliente.setIsAdmin(false);
        return clienteRepository.save(cliente);
    }
    public Page<Cliente> verClientesActivos(int pagina) {
        Pageable pageable = PageRequest.of(pagina, 6);
        return clienteRepository.findByActiveTrue(pageable);
    }

    public Page<Registros> verTodosRegistros(int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        return registrosRepository.findAll(pageable);
    }
    public Page<Registros> buscarRegistrosPorCliente(String buscar, int pagina) {
        Pageable pageable = PageRequest.of(pagina, 10);
        return registrosRepository.findByClienteNameContainingIgnoreCase(buscar, pageable);
    }

    public void guardarRegistro(Cliente cliente, String tipo) {
        Registros registro = new Registros();
        registro.setCliente(cliente);
        registro.setGasolinera(cliente.getGasStation());
        registro.setCreationDate(LocalDate.now());
        registro.setTipo(tipo);
        registrosRepository.save(registro);
    }
    public boolean existeUsername(String username, int idActual) {
        Cliente existente = clienteRepository.findByUsername(username);
        return existente != null && existente.getClientId() != idActual;
    }

    public Cliente findByUsername(String username) {
        return clienteRepository.findByUsername(username);
    }
    public List<Cliente> verTodosClientes() {
        return clienteRepository.findAll();
    }
    public Page<Cliente> buscarClientes(String buscar, int pagina) {
        Pageable pageable = PageRequest.of(pagina, 5);
        return clienteRepository
                .findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrUsernameContainingIgnoreCase(
                        buscar, buscar, buscar, pageable);
    }
}

