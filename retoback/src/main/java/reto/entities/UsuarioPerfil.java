package reto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(
    name = "usuario_perfiles",
    uniqueConstraints = @UniqueConstraint(columnNames = {"username", "id_perfil"})
)
@IdClass(UsuarioPerfilId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"usuario", "perfil"})
@EqualsAndHashCode(exclude = {"usuario", "perfil"})
public class UsuarioPerfil {

    @Id
    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_perfil", nullable = false)
    private Perfil perfil;

}
