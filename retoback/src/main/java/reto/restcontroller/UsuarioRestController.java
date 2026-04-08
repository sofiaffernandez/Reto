package reto.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reto.entities.Perfil;
import reto.entities.Usuario;
import reto.entities.UsuarioPerfil;
import reto.repository.PerfilRepository;
import reto.repository.UsuarioPerfilRepository;
import reto.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRestController {

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private UsuarioPerfilRepository usuarioPerfilRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private UsuarioService usuarioService;

    //usuarios/alta -> crear usuario
    @PostMapping("/alta")
    public ResponseEntity<?> alta(@RequestBody Usuario usuario) {
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            Usuario nuevoUsuario = usuarioService.createOne(usuario);
            if (nuevoUsuario != null) {
                // Asignar perfil CLIENTE (esto lo pongo automático, me imagino que debe ser asi, podemos revisar)
                Perfil perfilCliente = perfilRepository.findByNombre("CLIENTE");
                if (perfilCliente == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Perfil CLIENTE no existe");
                }
                UsuarioPerfil usuarioPerfil = UsuarioPerfil.builder()
                        .usuario(nuevoUsuario)
                        .perfil(perfilCliente)
                        .build();
                usuarioPerfilRepository.save(usuarioPerfil);
                return ResponseEntity.status(HttpStatus.CREATED).body(nuevoUsuario);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El usuario ya existe");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el usuario");
        }
    }

    //usuarios/detalle/{username}
    @GetMapping("/detalle/{username}")
    public ResponseEntity<?> detalle(@PathVariable String username) {
        try {
            Usuario usuario = usuarioService.findByUsername(username);
            if (usuario != null) {
                return ResponseEntity.ok(usuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el usuario");
        }
    }

    //usuarios/editar
    @PutMapping("/editar")
    public ResponseEntity<?> editar(@RequestBody Usuario usuario) {
        try {
            Usuario updateUsuario = usuarioService.updateOne(usuario);
            if (updateUsuario != null) {
                return ResponseEntity.ok(updateUsuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el usuario");
        }
    }

    //usuarios/eliminar/{username}
    @DeleteMapping("/eliminar/{username}")
    public ResponseEntity<?> eliminar(@PathVariable String username) {
        try {
            Usuario deleteUsuario = usuarioService.deleteOne(username);
            if (deleteUsuario != null) {
                return ResponseEntity.ok(deleteUsuario);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar el usuario");
        }
    }

    //usuarios/rol/{username}?nuevoRol=ROLE_ADMON
    @PutMapping("/rol/{username}")
    public ResponseEntity<?> cambiarRol(@PathVariable String username,
                                         @RequestParam String nuevoRol) {
        try {
            usuarioService.updateUserRole(username, nuevoRol);
            return ResponseEntity.ok("Rol actualizado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al cambiar el rol");
        }
    }
}
