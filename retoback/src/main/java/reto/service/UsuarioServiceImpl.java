package reto.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import reto.dto.AuthResponseDto;
import reto.dto.LoginRequestDto;
import reto.entities.Perfil;
import reto.entities.Usuario;
import reto.entities.UsuarioPerfil;
import reto.security.JwtService;
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

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

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

  @Transactional
  @Override
  public Usuario createUsuarioConPerfil(Usuario usuario, Perfil perfil) {
    // Comprobar duplicado por username
    if (usuarioRepository.existsById(usuario.getUsername())) {
      throw new IllegalArgumentException("El usuario ya existe");
    }
// Comprobar duplicado por email, no tiene mas logica?
    if (usuarioRepository.existsByEmailIgnoreCase(usuario.getEmail())) {
      throw new IllegalArgumentException("El email ya está registrado");
    }

    if (usuario.getEnabled() == null) {
      usuario.setEnabled(1);
    }

    if (usuario.getFechaRegistro() == null) {
      usuario.setFechaRegistro(LocalDate.now());
    }

    usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    Usuario saved = usuarioRepository.save(usuario);

    usuarioPerfilRepository.save(
        UsuarioPerfil.builder()
            .usuario(saved)
            .perfil(perfil)
            .build());

    return saved;
  }

  @Transactional
  @Override
  public AuthResponseDto registrarCliente(Usuario usuario) {
    Perfil perfilCliente = perfilRepository.findByNombre("ROLE_CLIENTE");
    if (perfilCliente == null) {
      throw new IllegalStateException("Perfil CLIENTE no existe");
    }

    Usuario nuevoUsuario = createUsuarioConPerfil(usuario, perfilCliente);
    List<String> roles = getRolesUsuario(nuevoUsuario);
    String token = jwtService.generateToken(nuevoUsuario.getUsername(), roles);

    return AuthResponseDto.builder()
        .token(token)
        .tokenType("Bearer")
        .username(nuevoUsuario.getUsername())
        .email(nuevoUsuario.getEmail())
        .roles(roles)
        .message("Usuario creado correctamente")
        .build();
  }

  @Override
  public AuthResponseDto login(LoginRequestDto loginRequest) {
    Usuario usuario = findByUsername(loginRequest.getUsername());
    if (usuario == null) {
      throw new IllegalArgumentException("Credenciales inválidas");
    }

    if (!passwordEncoder.matches(loginRequest.getPassword(), usuario.getPassword())) {
      throw new IllegalArgumentException("Credenciales inválidas");
    }

    List<String> roles = getRolesUsuario(usuario);
    String token = jwtService.generateToken(usuario.getUsername(), roles);

    return AuthResponseDto.builder()
        .token(token)
        .tokenType("Bearer")
        .username(usuario.getUsername())
        .email(usuario.getEmail())
        .roles(roles)
        .message("Login correcto")
        .build();
  }


}