package sptech.elderly.web.dto.usuario;

import org.mapstruct.Mapper;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    public UsuarioEntity criarFuncionario(CriarFuncionario novoFuncionario);

    public TipoUsuario mapTipoUsuario(Integer tipoUsuario);

    public Genero mapGenero(Integer genero);

//    public static UsuarioConsultaDto toDto(UsuarioEntity usuario){
//        UsuarioConsultaDto usuarioConsultaDto = new UsuarioConsultaDto();
//
//        usuarioConsultaDto.setId(usuario.getId());
//        usuarioConsultaDto.setNome(usuario.getNome());
//        usuarioConsultaDto.setEmail(usuario.getEmail());
//        usuarioConsultaDto.setDocumento(usuario.getDocumento());
//        usuarioConsultaDto.setTipoUsuario(usuario.getTipoUsuario().getNome());
//        usuarioConsultaDto.setGenero((usuario.getGenero() != null && usuario.getGenero().getNome() != null) ? usuario.getGenero().getNome() : "Sem Gênero");
//        usuarioConsultaDto.setCurriculos(CurriculoConsultaDto.converterLista(usuario.getCurriculos()));
//
//        return usuarioConsultaDto;
//    }

//    public static List<UsuarioConsultaDto> toDto(List<UsuarioEntity> usuarios){
//         List<UsuarioConsultaDto> usuarioConsultaDtos = usuarios
//                .stream()
//                .map(FuncionarioMapper:: toDto)
//                .collect(Collectors.toList());
//
//        return usuarioConsultaDtos;
//    }

}