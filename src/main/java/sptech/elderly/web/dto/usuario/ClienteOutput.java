package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record ClienteOutput(@NotNull Integer id,
                            @NotBlank String nome,
                            @NotBlank String email,
                            @NotBlank String documento,
                            @NotNull Date dataNascimento,
                            @NotBlank String biografia,
                            Integer genero,
                            EnderecoOutput endereco) implements Serializable {
}