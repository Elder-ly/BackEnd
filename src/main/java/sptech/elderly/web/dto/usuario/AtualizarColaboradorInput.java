package sptech.elderly.web.dto.usuario;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
public record AtualizarColaboradorInput(String nome,
                                        String email,
                                        String documento,
                                        LocalDate dataNascimento,
                                        String biografia,
                                        List<Integer> id) implements Serializable {
}