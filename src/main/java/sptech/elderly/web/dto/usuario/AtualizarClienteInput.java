package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import sptech.elderly.web.dto.endereco.AtualizarEnderecoInput;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record AtualizarClienteInput(@NotBlank String nome,
                                    @NotBlank String email,
                                    AtualizarEnderecoInput endereco) implements Serializable {
}