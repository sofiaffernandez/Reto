package reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import reto.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    Usuario findByUsername(String username);
    
}
