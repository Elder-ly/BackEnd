package sptech.elderly.web.dto.mensagem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.web.dto.usuario.UsuarioMapper;

import java.util.List;

@Getter @AllArgsConstructor @NoArgsConstructor
public class UsuarioConversaOutput {
    private Integer id;
    private String nome;
    private String fotoPerfil;
    @JsonIgnore
    private List<Curriculo> curriculos;

    public List<Especialidade> getEspecialidades() {
        return UsuarioMapper.mapCurriculosToEspecialidades(curriculos);
    }
}
