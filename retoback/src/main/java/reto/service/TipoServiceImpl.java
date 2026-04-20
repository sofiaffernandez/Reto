package reto.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reto.entities.Tipos;
import reto.repository.TipoRepository;

@Service
public class TipoServiceImpl implements TipoService {
  @Autowired
  private TipoRepository tipoRepo;

  @Override
  public List<Tipos> findAll() {
    return tipoRepo.findAll();
  }

  @Override
  public Tipos findById(Integer idTipo) {
    return tipoRepo.findById(idTipo).orElse(null);  
  }

  
  @Override
  public Tipos createOne(Tipos tipo) {
    if (tipoRepo.existsByNombreIgnoreCase(tipo.getNombre())) {
      throw new IllegalArgumentException("Ya existe un tipo con ese nombre");
    }
    return tipoRepo.save(tipo);
  }

  
  @Override
  public Tipos updateOne(Integer idTipo, Tipos tipo) {
    return tipoRepo.findById(idTipo)
        .map(existente -> {
          if (!existente.getNombre().equalsIgnoreCase(tipo.getNombre())
              && tipoRepo.existsByNombreIgnoreCase(tipo.getNombre())) {
            throw new IllegalArgumentException("Ya existe un tipo con ese nombre");
          }
          existente.setNombre(tipo.getNombre());
          existente.setDescripcion(tipo.getDescripcion());
          return tipoRepo.save(existente);
        })
        .orElse(null);
  }

 
  @Override
  public Tipos deleteOne(Integer idTipo) {
    Tipos tipo = tipoRepo.findById(idTipo).orElse(null);
    if (tipo != null) {
      tipoRepo.delete(tipo);
    }
    return tipo;
  }

}
