package reto.restcontroller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el tipo ");
    }
  }


}
