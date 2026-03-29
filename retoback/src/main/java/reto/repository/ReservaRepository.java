package reto.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import reto.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
  List<Reserva> findByEventoIdAndUsuarioUsername(Integer idEvento, String username);
  List<Reserva> findByEventoId(Integer idEvento);
  List<Reserva> findByUsuarioUsername(String username);
  List<Reserva> findByUsuarioUsernameAndEventoFechaInicioAfter(String username, LocalDate fecha);
}
