package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

public record CriarFuncionarioInput(@NotBlank String nome,
                                    @Email @NotBlank String email,
                                    @NotNull String dataNascimento,
                                    @NotBlank String documento,
                                    Integer tipoUsuario,
                                    Integer genero,
                                    CriarEnderecoInput endereco
                               ) {
}
