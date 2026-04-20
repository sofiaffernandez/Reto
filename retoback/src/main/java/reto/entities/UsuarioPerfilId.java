package reto.entities;

import java.io.Serializable;
import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioPerfilId implements Serializable {
    private String usuario;
    private Integer perfil;
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UsuarioPerfilId that = (UsuarioPerfilId) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(perfil, that.perfil);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, perfil);
    }
}

/*
 * Lo primero que debes anotar, en letras mayúsculas, es esto: UsuarioPerfilId
 * NO es una entidad. No tiene la etiqueta @Entity. Jamás se convertirá en una
 * tabla.
 * 
 * ¿Por qué lo hemos creado obligatoriamente entonces?
 * Imagina que en la tabla de Usuarios quieres buscar al usuario de la base de
 * datos usando Hibernate. Usarías su "llave", que es única. Tu código haría
 * algo así: usuarioRepository.findById( "paco@gmail.com" ); Fácil, tiene sólo 1
 * llave en esa tabla, un simple String.
 * 
 * Damos el salto a tu tabla real usuario_perfiles. Tu diagrama en MySQL dijo
 * que su Identificador Único son DOS cosas unidas a la vez (username y
 * id_perfil). Si tú quieres que Hibernate te busque qué perfil tiene un usuario
 * específico en esa tabla y decirle "Búscame el registro", ¿cómo se lo mandas?
 * Java no tiene una forma de mandar "dos variables sueltas" al findById. Las
 * reglas de Spring Data exigen que le ofrezcas un solo objeto identificador.
 * 
 * Ahí es donde entra nuestro archivo UsuarioPerfilId.java:
 * 
 * 1. Actúa como un cofre o un paquete envoltorio.
 * 2. Metemos dentro de esa maletita el String username y el Integer id_perfil.
 * 3. Le damos la maletita a la clase UsuarioPerfil.java
 * indicándole: @IdClass(UsuarioPerfilId.class) (Anotado arriba de tu Entity).
 * De esta manera, cuando vayas a programar el servicio y quieras encontrar el
 * perfil o borrarlo, podrás hacer: repository.deleteById( new
 * UsuarioPerfilId("paco", 1) );
 * 
 * En resumen para tus apuntes: En JPA, cuando una tabla (UsuarioPerfil) tiene
 * una clave primaria compuesta de varias columnas (Clave Foránea hacia Usuario
 * + Clave Foránea hacia Perfil), y no tiene su propio ID inventado, estamos
 * obligados por el framework a crear una clase auxiliar "Identificadora"
 * (UsuarioPerfilId) que empaquete las variables. Esa clase debe de implementar
 * Serializable indicando a Java que se puede "traducir" para moverse por la red
 * o escribirse en disco.
 */