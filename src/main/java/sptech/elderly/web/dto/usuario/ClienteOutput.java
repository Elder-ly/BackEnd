package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.entity.Usuario;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link Usuario}
 */
public record ClienteOutput(@NotNull Long id,
                            @NotBlank String nome,
                            @NotBlank String email,
                            @NotBlank String documento,
                            @NotNull LocalDate dataNascimento,
                            @NotBlank String biografia,
                            Long genero,
                            EnderecoOutput endereco) implements Serializable {
}