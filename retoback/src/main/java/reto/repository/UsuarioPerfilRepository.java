package reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reto.entities.UsuarioPerfil;
import reto.entities.Usuario;
import reto.entities.UsuarioPerfilId;
import java.util.List;

public interface UsuarioPerfilRepository extends JpaRepository<UsuarioPerfil, UsuarioPerfilId> {
    List<UsuarioPerfil> findByUsuario(Usuario usuario);
    void deleteByUsuario(Usuario usuario);
}
