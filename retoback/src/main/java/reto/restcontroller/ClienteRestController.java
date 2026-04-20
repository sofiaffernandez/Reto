package reto.restcontroller;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reto.entities.Evento;
import reto.entities.Reserva;
import reto.repository.ReservaRepository;
import reto.service.EventoService;


@RestController
@RequestMapping("/clientes")
public class ClienteRestController {
    @Autowired
    private EventoService eventoService;
    @Autowired
    private ReservaRepository reservaRepository;

    @GetMapping("/activos")
    public ResponseEntity<?> activos () {
      try {
        return ResponseEntity.ok(eventoService.findActivos());
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos activos");
      }
    }

    @GetMapping("/destacados")
     public ResponseEntity<?> destacados () {
      try {
        return ResponseEntity.ok(eventoService.findDestacados());
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos destacados");
      }
    }

    @GetMapping("/terminados")
    public ResponseEntity<?> getEventosTerminados() {
        try {
        return ResponseEntity.ok(eventoService.findTerminados());
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos terminados");
      }
    }

    @GetMapping("/cancelados")
    public ResponseEntity<?> cancelados () {
      try {
        return ResponseEntity.ok(eventoService.findActivos());
      } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al obtener el listado de eventos activos");
      }
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<Evento> getDetalleEvento(@PathVariable Integer id) {
        Evento evento = eventoService.findById(id);
        if (evento == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(evento);
    }

    @PostMapping("/reservar/{id}")
    public ResponseEntity<String> reservarEvento(@PathVariable Integer id,
                                                  @RequestParam int cantidad,
                                                  Principal principal) {
        try {
            eventoService.crearReserva(id, cantidad, principal.getName());
            return ResponseEntity.ok("Reserva realizada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al realizar la reserva.");
        }
    }

   

    @GetMapping("/misReservas")
    public List<Reserva> getMisReservas(Principal principal) {
        String username = principal.getName();
        LocalDate hoy = LocalDate.now();
        return reservaRepository.findByUsuarioUsernameAndEventoFechaInicioAfter(username, hoy);
    }

    @DeleteMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarReserva(@PathVariable Integer id, Principal principal) {
        Reserva reserva = reservaRepository.findById(id).orElse(null);
        if (reserva == null) {
            return ResponseEntity.notFound().build();
        }
        if (!reserva.getUsuario().getUsername().equals(principal.getName())) {
            return ResponseEntity.status(403).body("No puedes cancelar reservas de otros usuarios.");
        }
        reservaRepository.deleteById(id);
        return ResponseEntity.ok("Reserva cancelada correctamente.");
    }
}
