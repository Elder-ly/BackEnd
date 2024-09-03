package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record ColaboradorOutput(@NotNull Long id,
                                @NotBlank String nome,
                                @NotBlank String email,
                                @NotBlank String documento,
                                @NotBlank LocalDate dataNascimento,
                                String fotoPerfil,
                                String biografia,
                                EnderecoOutput endereco,
                                List<Especialidade> especialidades) implements Serializable {
}