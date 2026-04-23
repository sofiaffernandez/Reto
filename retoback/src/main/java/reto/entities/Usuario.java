package reto.entities;

import java.time.LocalDate; 
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"perfiles", "reservas"})
@EqualsAndHashCode(exclude = {"perfiles", "reservas"})
public class Usuario {
    @Id
    @Column(length = 45)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 30)
    private String nombre;

    @Column(length = 45)
    private String apellidos;

    @Column(length = 100)
    private String direccion;

    @Column(nullable = false)
    private Integer enabled;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "usuario")
    @com.fasterxml.jackson.annotation.JsonIgnoreProperties("usuario")
    private Set<UsuarioPerfil> perfiles;

    @OneToMany(mappedBy = "usuario")
    @JsonIgnore
    private Set<Reserva> reservas;

}
