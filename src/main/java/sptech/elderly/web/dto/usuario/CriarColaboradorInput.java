package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

import java.util.Date;
import java.util.List;

public record CriarColaboradorInput(@NotBlank String nome,
                                    @Email @NotBlank String email,
                                    @NotBlank String documento,
                                    @NotNull Date dataNascimento,
                                    Integer genero,
                                    Integer tipoUsuario,
                                    CriarEnderecoInput endereco,
                                    List<String> especialidades) {
}