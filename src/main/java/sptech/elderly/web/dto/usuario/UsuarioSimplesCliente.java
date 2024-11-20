package sptech.elderly.web.dto.usuario;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sptech.elderly.entity.Usuario;

import java.time.LocalDate;
import java.util.List;

@Getter @Setter @NoArgsConstructor
public class UsuarioSimplesCliente {

    private String nome;
    private String email;
    private String documento;
    private LocalDate dataNascimento;

    public UsuarioSimplesCliente(Usuario user) {
        this.nome = user.getNome();
        this.email = user.getEmail();
        this.documento = user.getDocumento();
        this.dataNascimento = user.getDataNascimento();
    }

    public static List<UsuarioSimplesCliente> buscarUsuarios(List<Usuario> users){
        return users.stream()
                .filter(usuario -> usuario.getTipoUsuario().getId() == 3)
                .map(UsuarioSimplesCliente::new)
                .toList();
    }
}