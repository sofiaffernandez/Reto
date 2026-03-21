package reto.service;
import java.util.List;

import reto.entities.Tipos;


public interface  TipoService {

  List <Tipos> findAll();
  Tipos findById(Integer idTipo);

}
