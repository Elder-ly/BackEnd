package sptech.elderly.web.dto.usuario;

import sptech.elderly.entity.Genero;
import sptech.elderly.entity.Residencia;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;
import sptech.elderly.web.dto.residencia.CriarResidenciaInput;

public class UsuarioMapper {

    public static UsuarioEntity of(CriarUsuarioInput novoUser, TipoUsuario tipoUsuarioId, Genero generoId) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(novoUser.nome());
        usuario.setEmail(novoUser.email());
        usuario.setSenha(novoUser.senha());
        usuario.setDocumento(novoUser.documento());
        usuario.setTipoUsuario(tipoUsuarioId);
        usuario.setGenero(generoId);

        return usuario;
    }

    public static UsuarioEntity of(CriarUsuarioInput novoUser, TipoUsuario tipoUsuarioId) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(novoUser.nome());
        usuario.setEmail(novoUser.email());
        usuario.setSenha(novoUser.senha());
        usuario.setDocumento(novoUser.documento());
        usuario.setTipoUsuario(tipoUsuarioId);
        usuario.setGenero(null);

        Residencia residencia = new Residencia();
        residencia.setUsuario(usuario);

        return usuario;
    }
}
