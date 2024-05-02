package sptech.elderly.web.dto.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.UsuarioEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO for {@link UsuarioEntity}
 */
public record AtualizarClienteInput(@NotBlank String nome,
                                    @NotBlank String email,
                                    @NotBlank String documento,
                                    @NotNull Date dataNascimento,
                                    GeneroDto genero) implements Serializable {
    /**
     * DTO for {@link Genero}
     */
    public record GeneroDto(String nome) implements Serializable {
    }
}