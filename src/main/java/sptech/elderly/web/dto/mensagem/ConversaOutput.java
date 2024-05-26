package sptech.elderly.web.dto.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sptech.elderly.entity.Especialidade;

import java.util.List;

@Getter @AllArgsConstructor
public class ConversaOutput {
    private Integer id;
    private String nome;
    private String fotoPerfil;
    private List<Especialidade> especialidades;
}
