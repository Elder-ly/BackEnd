package sptech.elderly.web.dto.usuario;

import sptech.elderly.web.dto.endereco.CriarEnderecoInput;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record AtualizarUsuarioInput(String nome,
                                    String email,
                                    String documento,
                                    LocalDate dataNascimento,
                                    String biografia,
                                    CriarEnderecoInput endereco,
                                    List<Integer> especialidades) implements Serializable {
}