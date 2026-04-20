package reto.restcontroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reto.entities.Tipos;
import reto.service.TipoService;


@RestController
@RequestMapping("/tipos")
public class TipoRestController {
  @Autowired
  private TipoService tipoService;

  //tipos/listado
  @GetMapping("/listado")
  public ResponseEntity<?> listado () {
    try {
      return ResponseEntity.ok(tipoService.findAll());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de tipos");
    }
  }

  //tipos/{id}
  @GetMapping("/{id}")
  public ResponseEntity<?> findById (@PathVariable Integer id) {
    try {
      Tipos tipo = tipoService.findById(id);
      if (tipo != null) {
        return ResponseEntity.ok(tipo);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo no encontrado");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el tipo");
    }
  }

  @PostMapping
  public ResponseEntity<?> create(@RequestBody Tipos tipo) {
    try {
      return ResponseEntity.status(HttpStatus.CREATED).body(tipoService.createOne(tipo));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el tipo");
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody Tipos tipo) {
    try {
      Tipos tipoActualizado = tipoService.updateOne(id, tipo);
      if (tipoActualizado == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo no encontrado");
      }
      return ResponseEntity.ok(tipoActualizado);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el tipo");
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable Integer id) {
    try {
      Tipos tipoEliminado = tipoService.deleteOne(id);
      if (tipoEliminado == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo no encontrado");
      }
      return ResponseEntity.ok(tipoEliminado);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar el tipo");
    }
  }


}
