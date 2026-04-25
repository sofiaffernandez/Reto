package reto.restcontroller;

import java.security.Principal;
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

import reto.dto.ReservaDTO;
import reto.service.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaRestController {

    @Autowired
    private ReservaService reservaService;

    // POST /reservas/reservar/{idEvento}?cantidad=X
    @PostMapping("/reservar/{idEvento}")
    public ResponseEntity<?> reservar(
            @PathVariable Integer idEvento,
            @RequestParam int cantidad,
            Principal principal) {
        try {
            String username = principal.getName();
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(reservaService.crearReserva(idEvento, cantidad, username));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al realizar la reserva.");
        }
    }

    // GET /reservas/misReservas
    @GetMapping("/misReservas")
    public ResponseEntity<?> misReservas(Principal principal) {
        try {
            String username = principal.getName();
            List<ReservaDTO> reservas = reservaService.findMisReservas(username);
            return ResponseEntity.ok(reservas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener las reservas.");
        }
    }

    // DELETE /reservas/cancelar/{idReserva}
    @DeleteMapping("/cancelar/{idReserva}")
    public ResponseEntity<?> cancelar(
            @PathVariable Integer idReserva,
            Principal principal) {
        try {
            String username = principal.getName();
            reservaService.cancelarReserva(idReserva, username);
            return ResponseEntity.ok("Reserva cancelada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al cancelar la reserva.");
        }
    }
}