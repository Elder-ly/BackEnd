package sptech.elderly.web.dto.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.elderly.entity.UsuarioEntity;

import java.util.List;

@Getter @Setter @NoArgsConstructor
public class UsuarioSimplesCliente {

    private String nome;
    private String email;
    private String documento;

    public UsuarioSimplesCliente(UsuarioEntity user) {
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.documento = user.getDocumento();
    }

    public static List<UsuarioSimplesCliente> buscarUsuarios(List<UsuarioEntity> users){
        return users.stream()
                .filter(usuario -> usuario.getTipoUsuario().getId() == 3)
                .map(UsuarioSimplesCliente::new)
                .toList();
    }
}