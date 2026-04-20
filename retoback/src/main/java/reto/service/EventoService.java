package reto.service;

import java.util.List;

import reto.entities.Evento;
import reto.entities.Reserva;

public interface  EventoService {
  
  //eventos/detalle/{id}
  Evento findById(Integer idEvento);

  //eventos/alta:
  Evento insertOne(Evento evento);

  ///eventos/editar/{id}
  Evento updateOne(Evento evento);
  
  void deleteOne(Integer idEvento);

  ///eventos/cancelar/{id}
  Evento cancelOne(Integer idEvento);

  List <Evento> findAll();

  //eventos/destacados
  List <Evento> findDestacados();

  //eventos/tipo/{tipo}
  List <Evento> findByTipo(String tipo);

  //eventos/activos
  List <Evento> findActivos();

  //eventos/cancelados
  List <Evento> findCancelados();

  List<Evento> findTerminados();

  List<Evento>findByEstado(String estado);

  List<Reserva> findMisReservas(String username);
  Reserva crearReserva(Integer idEvento, int cantidad, String username);
  Reserva cancelarReserva(Integer idReserva, String username);
}
