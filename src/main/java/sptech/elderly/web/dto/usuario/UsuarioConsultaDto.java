package sptech.elderly.web.dto.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link sptech.elderly.entity.UsuarioEntity}
 */
@Getter @Setter @NoArgsConstructor
public class UsuarioConsultaDto implements Serializable {
    Integer id;
    String nome;
    String email;
    String documento;
    String tipoUsuario;
    String genero;
    EnderecoOutput endereco;
    List<String> especialidades;
}