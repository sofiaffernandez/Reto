package reto.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario_perfiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioPerfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @MapsId("username")
    @JoinColumn(name = "username")
    private Usuario usuario;

    @ManyToOne
    @MapsId("idPerfil")
    @JoinColumn(name = "id_perfil")
    private Perfil perfil;

}
