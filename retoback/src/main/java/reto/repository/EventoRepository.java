package reto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import reto.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
  Evento findbyId(Integer idEvento);
  Evento insertOne(Evento evento);
  Evento updateOne(Evento evento);
  
  //eventos/tipo/{tipo}
  List<Evento> findByTipo(String tipo);

  //eventos/activos
  @Query("SELECT e FROM Evento e WHERE e.estado = 'activo'")
  List<Evento> findActivos();

  //cancelados
  @Query("SELECT e FROM Evento e WHERE e.estado = 'cancelado'")
  List<Evento> findCancelados();

  //destacadps
  @Query("SELECT e FROM Evento e WHERE e.estado = 'terminado'")
  List<Evento> findTerminados();

  //eventos/destacados
  @Query("SELECT e FROM Evento e WHERE e.destacado = 'S'")
  List<Evento> findDestacados();

   List<Evento> findByEstado(String estado);

  List<Evento> findByDestacadoAndEstado(String destacado, String estado);

  
}