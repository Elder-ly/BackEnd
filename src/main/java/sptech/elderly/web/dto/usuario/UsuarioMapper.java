package sptech.elderly.web.dto.usuario;

import sptech.elderly.entity.CurriculoConsultaDto;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

    public static UsuarioEntity ofFuncionarioEntity(CriarUsuarioInput novoUser, TipoUsuario tipoUsuarioId, Genero generoId) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(novoUser.nome());
        usuario.setEmail(novoUser.email());
        usuario.setDocumento(novoUser.documento());
        usuario.setTipoUsuario(tipoUsuarioId);
        usuario.setGenero(generoId);

        return usuario;
    }

    public static UsuarioEntity ofClienteEntity(CriarUsuarioInput novoUser, TipoUsuario tipoUsuarioId) {
        UsuarioEntity usuario = new UsuarioEntity();

        usuario.setNome(novoUser.nome());
        usuario.setEmail(novoUser.email());
        usuario.setDocumento(novoUser.documento());
        usuario.setTipoUsuario(tipoUsuarioId);
        usuario.setGenero(null);

        return usuario;
    }

    public static UsuarioConsultaDto toDto(UsuarioEntity usuario){
        UsuarioConsultaDto usuarioConsultaDto = new UsuarioConsultaDto();

        usuarioConsultaDto.setId(usuario.getId());
        usuarioConsultaDto.setNome(usuario.getNome());
        usuarioConsultaDto.setEmail(usuario.getEmail());
        usuarioConsultaDto.setDocumento(usuario.getDocumento());
        usuarioConsultaDto.setTipoUsuario(usuario.getTipoUsuario().getNome());
        usuarioConsultaDto.setGenero((usuario.getGenero() != null && usuario.getGenero().getNome() != null) ? usuario.getGenero().getNome() : "Sem Gênero");
        usuarioConsultaDto.setCurriculos(CurriculoConsultaDto.converterLista(usuario.getCurriculos()));

        return usuarioConsultaDto;
    }

    public static List<UsuarioConsultaDto> toDto(List<UsuarioEntity> usuarios){
         List<UsuarioConsultaDto> usuarioConsultaDtos = usuarios
                .stream()
                .map(UsuarioMapper:: toDto)
                .collect(Collectors.toList());

        return usuarioConsultaDtos;
    }

}
