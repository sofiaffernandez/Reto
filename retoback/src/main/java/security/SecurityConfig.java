package security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;

public class SecurityConfig {

/*
   @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
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

                // Registro de usuarios permitido a todos
                .requestMatchers("/usuarios/crear").permitAll()

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());  

        return http.build();
    }
*/
}
