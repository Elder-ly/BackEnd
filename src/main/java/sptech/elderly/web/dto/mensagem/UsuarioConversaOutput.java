package sptech.elderly.web.dto.mensagem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.Residencia;
import sptech.elderly.web.dto.endereco.EnderecoResumido;
import sptech.elderly.web.dto.usuario.UsuarioMapper;

import java.util.List;

@Getter @AllArgsConstructor @NoArgsConstructor
public class UsuarioConversaOutput {
    private Integer id;
    private String nome;
    private String fotoPerfil;
    @JsonIgnore
    private List<Residencia> residencias;
    @JsonIgnore
    private List<Curriculo> curriculos;

    public List<Especialidade> getEspecialidades() {
        return UsuarioMapper.mapCurriculosToEspecialidades(curriculos);
    }

    public EnderecoResumido getEndereco() {
        if (residencias.isEmpty()) return null;
        if (residencias.get(0).getEndereco() == null) return null;
        return new EnderecoResumido(
                residencias.get(0).getEndereco().getBairro(),
                residencias.get(0).getEndereco().getCidade());
    }
}
