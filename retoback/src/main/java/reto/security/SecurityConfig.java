package reto.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.web.SecurityFilterChain;

import jakarta.annotation.PostConstruct;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @PostConstruct
    public void init() {
        System.out.println("Cargando la configuración de seguridad...");
    }

  

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(request -> {
                    var config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowedOrigins(java.util.List.of(
                            "http://localhost:4200",
                            "https://reto.alvaropj.com"));
                    config.setAllowedMethods(java.util.List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
                    config.setAllowedHeaders(java.util.List.of("*"));
                    config.setAllowCredentials(true);
                    return config;
                }))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/usuarios/alta", "/login").permitAll()
                        .requestMatchers("/eventos", "/eventos/listado", "/eventos/detalle/**").permitAll()
                        .requestMatchers("/eventos/destacados", "/eventos/activos", "/eventos/cancelados",
                                "/eventos/terminados")
                        .permitAll()
                        .requestMatchers("/tipos/**").permitAll()
                        .requestMatchers("/eventos/alta", "/eventos/editar/**", "/eventos/cancelar/**",
                                "/eventos/eliminar/**")
                        .hasRole("ADMON")
                        .requestMatchers("/usuarios/**").hasRole("ADMON")
                        .requestMatchers("/reservas/**").hasAnyRole("CLIENTE", "ADMON")
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
