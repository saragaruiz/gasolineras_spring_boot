/*package org.gestion.proyecto_gasolinera.config;

import org.springframework.security.core.userdetails.User;
import org.gestion.proyecto_gasolinera.Cliente;
import org.gestion.proyecto_gasolinera.repositories.ClienteRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final ClienteRepository clienteRepository;

    public UserDetailServiceImpl(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Cliente cliente = clienteRepository.findByUsername(username);
        if(cliente == null) throw new UsernameNotFoundException("Usuario no encontrado");
        String rol = cliente.isAdmin()? "ROLE_ADMIN":"ROLE_USER";
        return new User(cliente.getUsername(), cliente.getPass(), List.of(new SimpleGrantedAuthority(rol)));

    }
}
*/