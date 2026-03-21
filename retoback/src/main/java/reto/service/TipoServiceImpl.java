package reto.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import reto.entities.Tipos;
import reto.repository.TipoRepository;

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




}
