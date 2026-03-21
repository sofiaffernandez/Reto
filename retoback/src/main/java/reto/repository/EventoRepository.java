package reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import reto.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer> {
  <Listt>Evento findbyId (Integer idEvento);
  Evento insertOne(Evento evento);
  Evento updateOne(Evento evento);
  


  
}