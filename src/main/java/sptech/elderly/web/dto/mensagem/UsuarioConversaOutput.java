package sptech.elderly.web.dto.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sptech.elderly.entity.Especialidade;

import java.util.List;

@Getter @NoArgsConstructor @AllArgsConstructor
public class UsuarioConversaOutput {
    private Integer id;
    private String nome;
    private String fotoPerfil;
    // private List<Especialidade> especialidades;
}
