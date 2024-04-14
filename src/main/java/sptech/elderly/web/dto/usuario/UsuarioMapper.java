package sptech.elderly.web.dto.usuario;

import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;

public class UsuarioMapper {

    public static UsuarioEntity ofFuncionario(CriarUsuarioInput novoUser, TipoUsuario tipoUsuarioId, Genero generoId) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(novoUser.nome());
        usuario.setEmail(novoUser.email());
        usuario.setSenha(novoUser.senha());
        usuario.setDocumento(novoUser.documento());
        usuario.setTipoUsuario(tipoUsuarioId);
        usuario.setGenero(generoId);

        return usuario;
    }

    public static UsuarioEntity ofCliente(CriarUsuarioInput novoUser, TipoUsuario tipoUsuarioId) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(novoUser.nome());
        usuario.setEmail(novoUser.email());
        usuario.setSenha(novoUser.senha());
        usuario.setDocumento(novoUser.documento());
        usuario.setTipoUsuario(tipoUsuarioId);
        usuario.setGenero(null);

        return usuario;
    }
}
