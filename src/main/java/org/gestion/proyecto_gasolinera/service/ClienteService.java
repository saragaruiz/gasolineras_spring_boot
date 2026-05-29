package org.gestion.proyecto_gasolinera.service;

import org.gestion.proyecto_gasolinera.Cliente;
import org.gestion.proyecto_gasolinera.Gasolinera;
import org.gestion.proyecto_gasolinera.Registros;
import org.gestion.proyecto_gasolinera.repositories.ClienteRepository;
import org.gestion.proyecto_gasolinera.repositories.GasolineraRepository;
import org.gestion.proyecto_gasolinera.repositories.RegistrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    GasolineraRepository gasolineraRepository;
    @Autowired
    RegistrosRepository registrosRepository;

    //Guardar cliente
    public Cliente guardarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }
    //ver clientes
    public List<Cliente> verClientes(){
        return clienteRepository.findAll();
    }

    //Actualizar cliente
    public Cliente actualizarCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    //borrar cliente
    public void borrarCliente(int id){
        clienteRepository.deleteById(id);
    }

    //asociar gasolinera
    public Cliente asociarGasolinera(int clientId, int gasStationId) {
        Cliente cliente = clienteRepository.findById(clientId).orElseThrow();
        Gasolinera gasolinera = gasolineraRepository.findById(gasStationId).orElseThrow();

        cliente.setGasStation(gasolinera);
        Cliente clienteActualizado = clienteRepository.save(cliente);
        Registros registros = new Registros();
        registros.setCliente(cliente);
        registros.setGasolinera(gasolinera);
        registros.setCreationDate(LocalDate.now());
        registros.setTipo("Asociado");
        registrosRepository.save(registros);
        return clienteActualizado;
    }
        //quitar asociacion
    public Cliente quitarAsociacion(int clientId){
        Cliente cliente = clienteRepository.findById(clientId).orElseThrow();
        Gasolinera gasolineraAnterior = cliente.getGasStation();
        cliente.setGasStation(null);
        Cliente clienteActualizado = clienteRepository.save(cliente);
        Registros registros = new Registros();
        registros.setCliente(cliente);
        registros.setGasolinera(gasolineraAnterior);
        registros.setCreationDate(LocalDate.now());
        registros.setTipo("Sin asociación");
        registrosRepository.save(registros);
        return clienteActualizado;
    }

    //Login
    public Cliente login(String username, String pass){
        Cliente cliente = clienteRepository.findByUsername(username);
        if(cliente == null){
            return null;
        }
        if(cliente.getPass().equals(pass)){
            return cliente;
        }
        return null;
    }
    //Dar baja
    public Cliente darBaja(String username){
        Cliente cliente = clienteRepository.findByUsername(username);
        cliente.setActive("0");
        return clienteRepository.save(cliente);
    }
    //dar alta
    public Cliente darAlta(String username){
        Cliente cliente = clienteRepository.findByUsername(username);
        cliente.setActive("1");
        return clienteRepository.save(cliente);
    }
    //cambiar contraseña
    public Cliente cambiarContraseña(String username, String nuevaPass){
        Cliente cliente = clienteRepository.findByUsername(username);
        cliente.setPass(nuevaPass);
        return clienteRepository.save(cliente);
    }
    //asignar admin
    public Cliente asignarAdmin(int clientId){
        Cliente cliente = clienteRepository.findById(clientId).orElseThrow();
        cliente.setIsAdmin(true);
        return clienteRepository.save(cliente);
    }
}