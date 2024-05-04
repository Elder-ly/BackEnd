package sptech.elderly.web.dto.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.elderly.entity.Endereco;
import sptech.elderly.entity.UsuarioEntity;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class UsuarioSimplesColaborador {

    private String nome;
    private String email;
    private String documento;
    private Endereco endereco;
    private List<String> especialidades;

    public UsuarioSimplesColaborador(UsuarioEntity usuario) {
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.documento = usuario.getDocumento();
        this.endereco = usuario.getResidencias().get(0).getEndereco();
        this.especialidades = usuario.getCurriculos().stream()
                .map(curriculo ->
                        curriculo.getEspecialidade().getNome())
                .toList();
    }

    public static List<UsuarioSimplesColaborador> buscarUsuarios(List<UsuarioEntity> usuarios){
        return usuarios.stream()
                .filter(usuario -> usuario.getTipoUsuario().getId() == 2)
                .map(UsuarioSimplesColaborador::new)
                .toList();
    }
}
