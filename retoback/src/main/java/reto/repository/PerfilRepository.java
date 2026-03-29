package reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import reto.entities.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    Perfil findByNombre(String nombre);
}
