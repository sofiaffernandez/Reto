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
public class ReservaDTO {
    private Integer idReserva;
    private String nombreEvento;
    private LocalDate fechaEvento;
    private int cantidad;
    private BigDecimal precioVenta;
}
