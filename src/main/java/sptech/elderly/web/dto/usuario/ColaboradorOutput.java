package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record ColaboradorOutput(@NotNull Integer id,
                                @NotBlank String nome,
                                @NotBlank String email,
                                @NotBlank String documento,
                                @NotNull Date dataNascimento,
                                @NotBlank String biografia,
                                Integer genero,
                                EnderecoOutput endereco,
                                List<String> especialidades) implements Serializable {
}