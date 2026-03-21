package reto.service;
import java.util.List;

import reto.entities.Evento;

public interface  EventoService {
  

  //eventos/detalle/{id}
  Evento findById(Integer idEvento);

  //eventos/alta:
  Evento insertOne(Evento evento);

  ///eventos/editar/{id}
  Evento updateOne(Evento evento);

  ///eventos/cancelar/{id}
  Evento cancelOne(Integer idEvento);

  List <Evento> findAll();
}
