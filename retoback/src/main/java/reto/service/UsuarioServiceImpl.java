package reto.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reto.entities.Perfil;
import reto.entities.Usuario;
import reto.entities.UsuarioPerfil;
import reto.repository.PerfilRepository;
import reto.repository.UsuarioPerfilRepository;
import reto.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private UsuarioPerfilRepository usuarioPerfilRepository;
  @Autowired
  private PerfilRepository perfilRepository;
  
  // CREATE Usuario createOne(Usuario usuario);
  @Override
  public Usuario createOne(Usuario usuario) {
    return usuarioRepository.save(usuario);
  }
  
  // READ Usuario findByUsername(String username);
  @Override
  public Usuario findByUsername(String username) {
    return usuarioRepository.findById(username).orElse(null);
  }
  
  // UPDATE Usuario updateOne(Usuario usuario);
  @Override
  public Usuario updateOne(Usuario usuario) {
    return usuarioRepository.findById(usuario.getUsername())
        .map(existente -> {
          existente.setEmail(usuario.getEmail());
          existente.setNombre(usuario.getNombre());
          existente.setApellidos(usuario.getApellidos());
          existente.setDireccion(usuario.getDireccion());
          return usuarioRepository.save(existente);
        })
        .orElse(null);

  }

  // DELETE Usuario deleteOne(String username);
  @Override
  public Usuario deleteOne(String username) {
    Usuario usuario = usuarioRepository.findById(username).orElse(null);
    if (usuario != null) {
      usuarioRepository.delete(usuario);
      return usuario;
    }
    return null;
  }
  
  @Override
  public List<String> getRolesUsuario(Usuario usuario) {
    // Busca los perfiles asociados al usuario y devuelve los nombres de los roles
    return usuarioPerfilRepository.findByUsuario(usuario)
            .stream()
            .map(up -> up.getPerfil().getNombre())
            .collect(Collectors.toList());
  }

  // UPDATE ROOOL Usuario updateRole(String username, String nuevoRol);
  @Override
  public void updateUserRole(String username, String nuevoRol) {

    Usuario usuario = usuarioRepository.findById(username).orElse(null);
    if (usuario == null)
      return;

    Perfil perfil = perfilRepository.findByNombre(nuevoRol);
    if (perfil == null)
      return;

    // Eliminar rol actual
    usuarioPerfilRepository.deleteByUsuario(usuario);

    // Crear nuevo rol
    UsuarioPerfil usuarioPerfil = UsuarioPerfil.builder()
        .usuario(usuario)
        .perfil(perfil)
        .build();

    usuarioPerfilRepository.save(usuarioPerfil);
  }

}