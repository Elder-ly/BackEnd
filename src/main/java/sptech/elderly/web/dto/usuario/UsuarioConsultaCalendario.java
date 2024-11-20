package sptech.elderly.web.dto.usuario;

import sptech.elderly.entity.Usuario;

import java.io.Serializable;

/**
 * DTO for {@link Usuario}
 */
public record UsuarioConsultaCalendario(Long id,
                                        String nome,
                                        String email) implements Serializable {
}