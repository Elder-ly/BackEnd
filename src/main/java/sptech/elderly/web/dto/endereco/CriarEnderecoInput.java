package sptech.elderly.web.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import sptech.elderly.entity.Endereco;

import java.io.Serializable;

/**
 * DTO for {@link Endereco}
 */
public record CriarEnderecoInput(Integer id,
                                 @NotBlank String cep,
                                 @NotBlank String logradouro,
                                 String complemento,
                                 @NotBlank String bairro,
                                 String numero,
                                 @NotBlank String cidade,
                                 @NotBlank String uf) implements Serializable {
}