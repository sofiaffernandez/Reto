package reto.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import reto.entities.Usuario;
import reto.service.UsuarioService;
@Service
public class CustomUserDetailService implements UserDetailsService{

     @Autowired
    private UsuarioService usuarioService;

    @Override
public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioService.findByUsername(username);
    if (usuario == null) {
        throw new UsernameNotFoundException("Usuario no encontrado: " + username);
    }

    // System.out.println("Username: " + usuario.getUsername());
    // System.out.println("Password BD: " + usuario.getPassword());
    
    // BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
    // System.out.println("¿Coincide con 1234?: " + encoder.matches("1234", usuario.getPassword()));
    

    // System.out.println("Password en BD: " + usuario.getPassword()); // ← añade esto

    // Extraemos los roles directamente del objeto usuario (que ya los trae gracias al JOIN FETCH)
    String[] roles = usuario.getPerfiles().stream()
        .map(up -> up.getPerfil().getNombre())
        .toArray(String[]::new);

    return User.builder()
        .username(usuario.getUsername())
        .password(usuario.getPassword())
        .authorities(roles)
        .build();
}

}
