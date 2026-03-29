package reto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import reto.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
  Evento findbyId(Integer idEvento);
  Evento insertOne(Evento evento);
  Evento updateOne(Evento evento);
  
//eventos/destacados
  List<Evento> findDestacados();

  //eventos/tipo/{tipo}
  List<Evento> findByTipo(String tipo);

  //eventos/activos
  List<Evento> findActivos();

  //eventos/cancelados
  List<Evento> findCancelados();

  List<Evento> findTerminados();
  
  List<Evento> findByEstado(String estado);
  List<Evento> findByDestacadoAndEstado(String destacado, String estado);

  
}