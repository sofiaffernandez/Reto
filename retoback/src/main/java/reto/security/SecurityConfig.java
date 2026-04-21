package reto.security;

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

   


    @PostConstruct
    public void init() {
        System.out.println("Cargando la configuración de seguridad...");
    }
    
    
    // El PasswordEncoder ya está definido en PasswordEncoderConfig.java
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
          .cors(cors -> cors.configurationSource(request -> {
            var config = new org.springframework.web.cors.CorsConfiguration();
            config.setAllowedOrigins(java.util.List.of("http://localhost:4200"));
            config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "PATCH", "DELETE"));
            config.setAllowedHeaders(java.util.List.of("*"));
            return config;
        }))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                //.requestMatchers("/eventos/listado").permitAll()
                .requestMatchers("/usuarios/alta", "/login").permitAll()
                .requestMatchers("/eventos", "/eventos/listado", "/eventos/detalle/**").permitAll()
                .requestMatchers("/eventos/destacados", "/eventos/activos", "/eventos/cancelados").permitAll()
                .requestMatchers("/eventos/alta", "/eventos/editar/**", "/eventos/cancelar/**", "/eventos/eliminar/**").hasRole("ADMON")
                .requestMatchers("/usuarios/**").hasRole("ADMON")
                .requestMatchers("/clientes/**").hasRole("CLIENTE")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
