package sptech.elderly.web.dto.usuario;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record UsuarioConsultaCalendario(Long id,
                                        String nome,
                                        String email) implements Serializable {
}