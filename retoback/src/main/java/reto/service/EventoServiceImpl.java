package reto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reto.entities.Evento;
import reto.repository.EventoRepository;

@Service
public class EventoServiceImpl implements EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Override
    public Evento findById(Integer idEvento) {
        return eventoRepository.findById(idEvento).orElse(null);
    }

    @Override
    public List<Evento> findAll() {
        return eventoRepository.findAll();
    }

    @Override
    public Evento insertOne(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Override
    public Evento updateOne(Evento evento) {
        return eventoRepository.findById(evento.getIdEvento())
                .map(existente -> {
                    existente.setNombre(evento.getNombre());
                    existente.setDescripcion(evento.getDescripcion());
                    existente.setFechaInicio(evento.getFechaInicio());
                    existente.setDuracion(evento.getDuracion());
                    existente.setDireccion(evento.getDireccion());
                    existente.setEstado(evento.getEstado());
                    existente.setDestacado(evento.getDestacado());
                    existente.setAforoMaximo(evento.getAforoMaximo());
                    existente.setMinimoAsistencia(evento.getMinimoAsistencia());
                    existente.setPrecio(evento.getPrecio());
                    existente.setTipo(evento.getTipo());
                    return eventoRepository.save(existente);
                })
                .orElse(null);
    }

    @Override
    public Evento cancelOne(Integer idEvento) {
        Evento evento = eventoRepository.findById(idEvento).orElse(null);
        if (evento != null) {
            evento.setEstado("cancelado");
            return eventoRepository.save(evento);
        }
        return null;
    }

    @Override
    public List<Evento> findDestacados() {
        return eventoRepository.findByDestacado("S");
    }

    @Override
    public List<Evento> findByTipo(String tipo) {
        return eventoRepository.findByTipoNombre(tipo);
    }

    @Override
    public List<Evento> findActivos() {
        return eventoRepository.findByEstado("ACTIVO");
    }

    @Override
    public List<Evento> findCancelados() {
        return eventoRepository.findByEstado("CANCELADO");
    }

    @Override
    public List<Evento> findTerminados() {
        return eventoRepository.findByEstado("TERMINADO");
    }

    @Override
    public List<Evento> findByEstado(String estado) {
        return eventoRepository.findByEstado(estado);
    }

}