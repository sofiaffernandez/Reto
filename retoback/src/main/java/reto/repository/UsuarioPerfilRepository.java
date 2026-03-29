package reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reto.entities.UsuarioPerfil;
import reto.entities.Usuario;
import java.util.List;

public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, Integer> {
    List<UsuarioPerfil> findByUsuario(Usuario usuario);
    void deleteByUsuario(Usuario usuario);
}
