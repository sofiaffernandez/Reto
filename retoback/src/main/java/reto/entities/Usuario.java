package reto.entities;

import java.time.LocalDate; 
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {
    @Id
    @Column(length = 45)
    private String username;

    @Column(length = 255, nullable = false)
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
    private Set<UsuarioPerfil> perfiles;

    @OneToMany(mappedBy = "usuario")
    private Set<Reserva> reservas;

}
