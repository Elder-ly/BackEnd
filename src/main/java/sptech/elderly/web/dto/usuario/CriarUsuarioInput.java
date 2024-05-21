package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link UsuarioEntity}
 */
public record CriarUsuarioInput(@NotBlank String nome,
                                @NotBlank String email,
                                @NotBlank String documento,
                                LocalDate dataNascimento,
                                String biografia,
                                String fotoPerfil,
                                Integer tipoUsuario,
                                Integer genero,
                                CriarEnderecoInput endereco) implements Serializable {
}