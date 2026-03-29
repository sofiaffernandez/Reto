package reto.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import reto.entities.Usuario;
import reto.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    //usuarios/alta -> crear usuario
    @PostMapping("/alta")
    public ResponseEntity<?> alta(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.createOne(usuario);
            if (nuevoUsuario != null) {
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
