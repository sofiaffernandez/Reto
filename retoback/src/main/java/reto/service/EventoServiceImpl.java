package reto.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import reto.entities.Evento;
import reto.repository.EventoRepository;


public class EventoServiceImpl implements EventoService {

  @Autowired
  private EventoRepository eventoRepo;

  @Override
  public Evento findById(Integer idEvento) {
    return eventoRepo.findById(idEvento).orElse(null);
  }

  @Override
  public List<Evento> findAll() {
    return eventoRepo.findAll();
  }

  @Override
  public Evento insertOne(Evento evento) {
    return eventoRepo.insertOne(evento);
  }

  @Override
  public Evento updateOne(Evento evento) {
    return eventoRepo.updateOne(evento);
  }

  @Override
  public Evento cancelOne(Integer idEvento) {
    Evento evento = eventoRepo.findById(idEvento).orElse(null);
    if (evento != null) {
      evento.setEstado("cancelado");
      return eventoRepo.updateOne(evento);
    }
    return null;
  }

  @Override
  public List<Evento> findDestacados() {
    return eventoRepo.findDestacados();
  }

  @Override
  public List<Evento> findByTipo(String tipo) { 
    return eventoRepo.findByTipo(tipo);
  }

  @Override
  public List<Evento> findActivos() {
    return eventoRepo.findActivos();
  }

  @Override
  public List<Evento> findCancelados() {
    return eventoRepo.findCancelados();
  }

  
}