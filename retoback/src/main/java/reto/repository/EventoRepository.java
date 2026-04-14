package reto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import reto.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {

  List<Evento> findByEstado(String estado);

  List<Evento> findByDestacadoAndEstado(String destacado, String estado);

  List<Evento> findByTipoNombre(String nombreTipo);

  List<Evento> findByDestacado(String destacado);

}