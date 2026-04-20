package reto.service;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reto.entities.Evento;
import reto.entities.Reserva;
import reto.entities.Usuario;
import reto.repository.EventoRepository;
import reto.repository.ReservaRepository;
import reto.repository.UsuarioRepository;

@Service
public class EventoServiceImpl implements EventoService {
    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


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
            evento.setEstado("CANCELADO");
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
        return eventoRepository.findByTipoNombreIgnoreCase(tipo);
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

    @Override
    public void deleteOne(Integer id) {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento != null) {
            eventoRepository.deleteById(id);
           
        }
        
    }

    @Override
    public Reserva crearReserva(Integer idEvento, int cantidad, String username) {
        if (cantidad < 1 || cantidad > 10) {
            throw new IllegalArgumentException("No se permite reservar más de 10 plazas por reserva.");
        }

        Evento evento = eventoRepository.findById(idEvento)
                .orElseThrow(() -> new IllegalArgumentException("Evento no encontrado."));

        int totalReservas = reservaRepository.findByEvento(evento)
                .stream().mapToInt(Reserva::getCantidad).sum();
        if (totalReservas + cantidad > evento.getAforoMaximo()) {
            throw new IllegalArgumentException("No hay suficientes plazas disponibles.");
        }

        List<Reserva> reservasCliente = reservaRepository
                .findByEventoIdEventoAndUsuarioUsername(idEvento, username);
        int reservasPrevias = reservasCliente.stream().mapToInt(Reserva::getCantidad).sum();
        if (reservasPrevias >= 10) {
            throw new IllegalArgumentException("Ya tienes 10 plazas reservadas para este evento.");
        }
        if (reservasPrevias + cantidad > 10) {
            throw new IllegalArgumentException("No puedes superar 10 plazas en total para este evento.");
        }

        Usuario usuario = usuarioRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));

        Reserva reserva = new Reserva();
        reserva.setEvento(evento);
        reserva.setUsuario(usuario);
        reserva.setCantidad(cantidad);
        return reservaRepository.save(reserva);
    }
}