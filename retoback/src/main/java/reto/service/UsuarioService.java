package reto.service;

import java.util.List;

import reto.dto.AuthResponseDto;
import reto.dto.LoginRequestDto;
import reto.entities.Perfil;
import reto.entities.Usuario;

public interface UsuarioService {
  List<String> getRolesUsuario(Usuario usuario);

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

  AuthResponseDto registrarCliente(Usuario usuario);

  AuthResponseDto login(LoginRequestDto loginRequest);
}
