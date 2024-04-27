package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

import java.util.List;

public record CriarFuncionario(@NotBlank String nome,
                               @Email @NotBlank String email,
                               @NotBlank String documento,
                               Integer genero,
                               Integer tipoUsuario,
                               CriarEnderecoInput  criarEnderecoInput,
                               CriarEnderecoInput endereco,
                               List<@NotBlank String> especialidades) {
}
