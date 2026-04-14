package reto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario_perfiles")
@IdClass(UsuarioPerfilId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioPerfil {

    @Id
    @ManyToOne
    @JoinColumn(name = "username")
    private Usuario usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

}
