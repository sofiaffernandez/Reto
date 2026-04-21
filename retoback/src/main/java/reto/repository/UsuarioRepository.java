package reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import reto.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {
    
    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.perfiles up LEFT JOIN FETCH up.perfil WHERE u.username = :username")
    Usuario findByUsernameWithPerfiles(@Param("username") String username);

    Usuario findByUsername(String username);
}
