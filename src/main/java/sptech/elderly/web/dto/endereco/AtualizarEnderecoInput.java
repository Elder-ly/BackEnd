package sptech.elderly.web.dto.endereco;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.Endereco}
 */
public record AtualizarEnderecoInput(@NotBlank String cep,
                                     @NotBlank String logradouro,
                                     @NotBlank String bairro,
                                     @NotBlank String numero,
                                     @NotBlank String complemento,
                                     @NotBlank String cidade,
                                     @NotBlank String uf) implements Serializable {
}