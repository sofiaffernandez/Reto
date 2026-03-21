package reto.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class EventoDto {

	private Integer idEvento;

	private String nombre;

	private String descripcion;

	private LocalDate fechaInicio;

	private Integer duracion;

	private String direccion;

	private String estado;

	private String destacado;

	private Integer aforoMaximo;

	private Integer minimoAsistencia;

	private BigDecimal precio;
  
	private Integer idTipo;
}
