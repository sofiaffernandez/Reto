package reto.service;

import java.util.List;
import reto.dto.ReservaDTO;
import reto.entities.Reserva;

public interface ReservaService {
    Reserva crearReserva(Integer idEvento, int cantidad, String username);
    Reserva cancelarReserva(Integer idReserva, String username);
    List<ReservaDTO> findMisReservas(String username);
}