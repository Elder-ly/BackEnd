package sptech.elderly.web.dto.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.elderly.entity.UsuarioEntity;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class UsuarioSimples {

    private String nome;
    private String email;
    private String documento;
    private String senha;

    public UsuarioSimples(UsuarioEntity user) {
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.documento = user.getDocumento();
        this.senha = user.getSenha();
    }

    public static List<UsuarioSimples> buscarUsuarios(List<UsuarioEntity> users){
        return users.stream()
                .filter(usuario -> usuario.getTipoUsuario().getId() == 3)
                .map(UsuarioSimples::new)
                .toList();
    }
}