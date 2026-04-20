package reto.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import reto.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
 // Optional<Evento> findById(Integer idEvento);
  //Evento insertOne(Evento evento);
  //Evento updateOne(Evento evento);
  
  //eventos/tipo/{tipo}
  List<Evento> findByTipo(String tipo);

  List<Evento> findByEstado(String estado);

  List<Evento> findByDestacadoAndEstado(String destacado, String estado);

  // List<Evento> findByEstado(String estado);

  List<Evento> findByDestacado(String destacado);

}