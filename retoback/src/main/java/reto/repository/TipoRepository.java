package reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import reto.entities.Tipos;

public interface TipoRepository  extends JpaRepository<Tipos, Integer> {
	boolean existsByNombreIgnoreCase(String nombre);

}
