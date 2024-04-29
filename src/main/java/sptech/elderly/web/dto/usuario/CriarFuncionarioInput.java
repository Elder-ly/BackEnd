package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

import java.util.List;

public record CriarFuncionarioInput(@NotBlank String nome,
                                    @Email @NotBlank String email,
                                    @NotBlank String documento,
                                    Integer tipoUsuario,
                                    Integer genero,
                                    CriarEnderecoInput endereco,
                                    List<@NotBlank String> especialidades
                               ) {
}
