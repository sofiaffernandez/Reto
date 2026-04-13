package reto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "usuario_perfiles",
    uniqueConstraints = @UniqueConstraint(columnNames = {"username", "id_perfil"})
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioPerfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

}
