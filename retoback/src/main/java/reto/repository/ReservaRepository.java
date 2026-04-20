package reto.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import reto.entities.Evento;
import reto.entities.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
  List<Reserva> findByEvento(Evento evento);
  List<Reserva> findByEventoIdEventoAndUsuarioUsername(Integer idEvento, String username);
  List<Reserva> findByEventoIdEvento(Integer idEvento);
  List<Reserva> findByUsuarioUsername(String username);
  List<Reserva> findByUsuarioUsernameAndEventoFechaInicioAfter(String username, LocalDate fecha);
}
