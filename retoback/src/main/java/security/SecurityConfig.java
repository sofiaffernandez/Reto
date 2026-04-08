package security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;
import reto.entities.Usuario;
import reto.service.UsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

      @Autowired
    private UsuarioService usuarioService;


    @PostConstruct
    public void init() {
        System.out.println("Cargando la configuración de seguridad...");
    }
    
    @Bean
     PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }
     
    @Bean
     UserDetailsService userDetailsService() {
        return username -> {
            System.out.println("Buscando usuario: " + username); 

            Usuario usuario = usuarioService.findByUsername(username);
            if (usuario == null) {
                System.out.println("Usuario no encontrado: " + username);
                throw new RuntimeException("Usuario no encontrado");
            }

            String[] roles = usuarioService.getRolesUsuario(usuario)
                .stream()
                .map(role -> role) 
                .toArray(String[]::new);

            System.out.println("Usuario encontrado: " + usuario.getUsername() + " con roles: " + Arrays.toString(roles)); 

            return User.builder()
                    .username(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(roles)
                    .build();
        };
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                // Invitado: 
                .requestMatchers("/eventos", "/eventos/", "/eventos/detalle/**").permitAll()
                .requestMatchers("/eventos/destacados", "/eventos/activos", "/eventos/cancelados").permitAll()

                // admin: solo ADMIN
                .requestMatchers("/eventos/alta", "/eventos/editar/**", "/eventos/cancelar/**").hasRole("ADMIN")
                .requestMatchers("/usuarios/**").hasRole("ADMIN")

                // Cliente: acceso a rutas de clientes
                .requestMatchers("/clientes/**").hasRole("CLIENTE")

                // Registro de usuarios permitido a todos (login y registro)
                .requestMatchers("/usuarios/crear", "/login").permitAll()

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
