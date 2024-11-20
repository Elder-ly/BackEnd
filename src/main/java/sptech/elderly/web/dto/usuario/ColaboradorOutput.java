package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.Usuario;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link Usuario}
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