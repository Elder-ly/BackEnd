package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

import java.time.LocalDate;

public record CriarColaboradorInput(@NotBlank String nome,
                                    @Email @NotBlank String email,
                                    @NotBlank String documento,
                                    @NotNull LocalDate dataNascimento,
                                    Integer genero,
                                    Integer tipoUsuario,
                                    CriarEnderecoInput endereco) {
}