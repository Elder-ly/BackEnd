package sptech.elderly.web.dto.residencia;

import sptech.elderly.entity.Endereco;
import sptech.elderly.entity.UsuarioEntity;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.Residencia}
 */
public record CriarResidenciaInput(UsuarioEntity usuario,
                                   Endereco endereco) implements Serializable {
}