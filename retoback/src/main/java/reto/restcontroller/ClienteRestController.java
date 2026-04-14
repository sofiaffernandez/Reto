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
import reto.entities.Usuario;
import reto.repository.ReservaRepository;
import reto.repository.UsuarioRepository;
import reto.service.EventoService;


@RestController
@RequestMapping("/clientes")
public class ClienteRestController {
    @Autowired
    private EventoService eventoService;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

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
    public ResponseEntity<String> reservarEvento(@PathVariable Integer id, @RequestParam int cantidad, Principal principal) {
        if (cantidad < 1 || cantidad > 10) {
            return ResponseEntity.badRequest().body("No se permite reservar más de 10 plazas por reserva.");
        }
        Evento evento = eventoService.findById(id);
        if (evento == null) {
            return ResponseEntity.badRequest().body("Evento no encontrado.");
        }
        int totalReservas = reservaRepository.findByEvento(evento).stream().mapToInt(Reserva::getCantidad).sum();
        if (totalReservas + cantidad > evento.getAforoMaximo()) {
            return ResponseEntity.badRequest().body("No hay suficientes plazas disponibles para este evento.");
        }
        String username = principal.getName();
        List<Reserva> reservasCliente = reservaRepository.findByEventoAndUsuarioUsername(evento, username);
        int reservasPrevias = reservasCliente.stream().mapToInt(Reserva::getCantidad).sum();
        if (reservasPrevias >= 10) {
            return ResponseEntity.badRequest().body("Ya tienes una reserva de 10 plazas para este evento.");
        }
        if (reservasPrevias + cantidad > 10) {
            return ResponseEntity.badRequest().body("No puedes superar 10 plazas reservadas en total para este evento.");
        }
        Usuario usuario = usuarioRepository.findById(username).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("Usuario no encontrado.");
        }
        Reserva reserva = new Reserva();
        reserva.setEvento(evento);
        reserva.setUsuario(usuario);
        reserva.setCantidad(cantidad);
        reservaRepository.save(reserva);
        return ResponseEntity.ok("Reserva realizada correctamente.");
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
