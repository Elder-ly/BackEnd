package sptech.elderly.web.dto.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
@Getter @Setter @NoArgsConstructor
public class UsuarioConsultaDto implements Serializable {
    private Long id;
    private String nome;
    private String email;
    private String documento;
    private LocalDate dataNascimento;
    private String biografia;
    private String fotoPerfil;
    private Long tipoUsuario;
    private Long genero;
    private EnderecoOutput endereco;
    private List<Especialidade> especialidades;
}