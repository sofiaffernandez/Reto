package reto.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController; 

import reto.dto.EventoDto;
import reto.entities.Evento;
import reto.entities.Tipos;
import reto.service.EventoService;

@RestController
@RequestMapping("/eventos")
public class EventoRestController {
  @Autowired
  private EventoService eventoService;

  //eventos/alta: crear un nuevo evento
  @PostMapping("/alta")
  public ResponseEntity<?> alta (@RequestBody EventoDto eventoDto) {
      Evento evento = new Evento();
      evento.setNombre(eventoDto.getNombre());
      evento.setDescripcion(eventoDto.getDescripcion());
      evento.setFechaInicio(eventoDto.getFechaInicio());
      evento.setDuracion(eventoDto.getDuracion());
      evento.setDireccion(eventoDto.getDireccion());
      evento.setEstado(eventoDto.getEstado());
      evento.setDestacado(eventoDto.getDestacado());
      evento.setAforoMaximo(eventoDto.getAforoMaximo());
      evento.setMinimoAsistencia(eventoDto.getMinimoAsistencia());
      evento.setPrecio(eventoDto.getPrecio());
      Tipos tipo = new Tipos();
      tipo.setIdTipo(eventoDto.getIdTipo());
      evento.setTipo(tipo);

    try {
      Evento nuevoEvento = eventoService.insertOne(evento);
      return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEvento);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear el evento");
    }
  }
  

  ///eventos/editar/{id}
   @PutMapping("/editar/{id}")
   public ResponseEntity<?> editar (@PathVariable Integer id, @RequestBody Evento evento) {
    try {
      Evento updateEvento = eventoService.updateOne(evento);
      if (updateEvento != null) {
        return ResponseEntity.ok(updateEvento);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no encontrado");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar el evento");
    }
  } 

  ///eventos/cancelar/{id}
  @PutMapping("/cancelar/{id}")
  public ResponseEntity<?> cancelar (@PathVariable Integer id) {
    try {
      Evento cancelEvento = eventoService.cancelOne(id);
      if (cancelEvento != null) {
        return ResponseEntity.ok(cancelEvento);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no encontrado");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al cancelar el evento");
    }
  }

  //eventos/detalle/{id} 
  @GetMapping("/detalle/{id}")
  public ResponseEntity<?> detalle (@PathVariable Integer id) {
    try {
      Evento evento = eventoService.findById(id);
      if (evento != null) {
        return ResponseEntity.ok(evento);
      } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Evento no encontrado");
      }
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el evento");
    }
  }

  //eventos/listado
  @GetMapping("/listado")
  public ResponseEntity<?> listado () {
    try {
      return ResponseEntity.ok(eventoService.findAll());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos");
    }
  }

  //eventos/destacados
  @GetMapping("/destacados")
  public ResponseEntity<?> destacados () {
    try {
      return ResponseEntity.ok(eventoService.findDestacados());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos destacados");
    }
  }
  
  //eventos/activos
  @GetMapping("/activos")
  public ResponseEntity<?> activos () {
    try {
      return ResponseEntity.ok(eventoService.findActivos());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos activos");
    }
  }

  //eventos/cancelados
  @GetMapping("/cancelados")  
  public ResponseEntity<?> cancelados () {
    try {
      return ResponseEntity.ok(eventoService.findCancelados());
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos cancelados");
    }
  }

  //eventos/tipo/{tipo}
  @GetMapping("/tipo/{tipo}")
  public ResponseEntity<?> tipo (@PathVariable String tipo) {
    try {
      return ResponseEntity.ok(eventoService.findByTipo(tipo));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos por tipo");
    }
  }


  // Otros endpoitns que creo que vamos a neceseitar 
  //eventos/buscar filtros //busqueda
  
  //Crud de usuarios 

  
  



}
