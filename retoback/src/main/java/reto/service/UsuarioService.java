package reto.service;

import java.util.List;

import reto.entities.Perfil;
import reto.entities.Usuario;

public interface UsuarioService {
  List<String> getRolesUsuario(Usuario usuario);

  // CRUD USUARIO ADMIN

  // CREATE
  Usuario createOne(Usuario usuario);

  // READ
  Usuario findByUsername(String username);

  // UPDATE
  Usuario updateOne(Usuario usuario);

  // DELETE
  Usuario deleteOne(String username);

  // buscar por rol y modificar el rol de un usuario
  // List<UsuarioPerfilDto> findByRole(String role);
  void updateUserRole(String username, String nuevoRol);

  Usuario createUsuarioConPerfil(Usuario usuario, Perfil perfil);
}
