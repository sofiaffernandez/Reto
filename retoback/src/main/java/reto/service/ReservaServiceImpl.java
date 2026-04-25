package reto.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reto.dto.ReservaDTO;
import reto.entities.Evento;
import reto.entities.Reserva;
import reto.entities.Usuario;
import reto.repository.EventoRepository;
import reto.repository.ReservaRepository;
import reto.repository.UsuarioRepository;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Reserva crearReserva(Integer idEvento, int cantidad, String username) {
        // Validar cantidad
        if (cantidad < 1 || cantidad > 10) {
            throw new IllegalArgumentException("La cantidad debe estar entre 1 y 10.");
        }

        // Obtener evento y usuario
        Evento evento = eventoRepository.findById(Objects.requireNonNull(idEvento))
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado."));

        Usuario usuario = usuarioRepository.findById(Objects.requireNonNull(username))
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        // Comprobar plazas disponibles
        int totalReservado = reservaRepository.findByEvento(evento)
                .stream()
                .mapToInt(Reserva::getCantidad)
                .sum();

        if (totalReservado + cantidad > evento.getAforoMaximo()) {
            throw new IllegalArgumentException("No hay suficientes plazas disponibles.");
        }

        // Comprobar límite por usuario (máx 10 plazas por evento)
        List<Reserva> reservasCliente = reservaRepository
                .findByEventoIdEventoAndUsuarioUsername(idEvento, username);
        int reservasPrevias = reservasCliente.stream().mapToInt(Reserva::getCantidad).sum();

        if (reservasPrevias >= 10) {
            throw new IllegalArgumentException("Ya tienes 10 plazas reservadas para este evento.");
        }
        if (reservasPrevias + cantidad > 10) {
            throw new IllegalArgumentException("No puedes superar 10 plazas en total para este evento.");
        }

        // Calcular precio
        BigDecimal precioVenta = evento.getPrecio().multiply(BigDecimal.valueOf(cantidad));

        // Crear y guardar reserva
        Reserva reserva = Reserva.builder()
                .evento(evento)
                .usuario(usuario)
                .cantidad(cantidad)
                .precioVenta(precioVenta)
                .build();

        return reservaRepository.save(reserva);
    }

    @Override
    public Reserva cancelarReserva(Integer idReserva, String username) {
        Reserva reserva = reservaRepository.findById(Objects.requireNonNull(idReserva))
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada."));

        if (!reserva.getUsuario().getUsername().equals(username)) {
            throw new IllegalArgumentException("No puedes cancelar reservas de otros usuarios.");
        }

        reservaRepository.delete(reserva);
        return reserva;
    }

    @Override
    public List<ReservaDTO> findMisReservas(String username) {
        return reservaRepository.findByUsuarioUsername(username)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ReservaDTO toDTO(Reserva r) {
        return ReservaDTO.builder()
                .idReserva(r.getIdReserva())
                .nombreEvento(r.getEvento().getNombre())
                .fechaEvento(r.getEvento().getFechaInicio())
                .cantidad(r.getCantidad())
                .precioVenta(r.getPrecioVenta())
                .build();
    }
}