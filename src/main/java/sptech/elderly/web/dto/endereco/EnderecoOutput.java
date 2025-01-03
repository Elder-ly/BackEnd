package sptech.elderly.web.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.entity.Endereco;

import java.io.Serializable;

/**
 * DTO for {@link Endereco}
 */
public record EnderecoOutput(@NotNull Long id,
                             @NotBlank String cep,
                             @NotBlank String logradouro,
                             @NotBlank String bairro,
                             @NotBlank String numero,
                             @NotBlank String complemento,
                             @NotBlank String cidade,
                             @NotBlank String uf) implements Serializable {
}