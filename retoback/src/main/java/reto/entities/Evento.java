package reto.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "eventos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Evento {
  
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_evento")
	private Integer idEvento;

	@Column(name = "nombre", length = 50)
	private String nombre;

	@Column(name = "descripcion", length = 200)
	private String descripcion;

	@Column(name = "fecha_inicio")
	private LocalDate fechaInicio;

	@Column(name = "duracion")
	private Integer duracion;

	@Column(name = "direccion", length = 100)
	private String direccion;

	@Column(name = "estado", length = 10)
	private String estado;

	@Column(name = "destacado", length = 1)
	private String destacado;

	@Column(name = "aforo_maximo")
	private Integer aforoMaximo;

	@Column(name = "minimo_asistencia")
	private Integer minimoAsistencia;

	@Column(name = "precio", precision = 9, scale = 2)
	private BigDecimal precio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo")
	private Tipos tipo;
}
