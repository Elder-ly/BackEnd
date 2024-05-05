package sptech.elderly.web.dto.usuario;


import lombok.RequiredArgsConstructor;
import sptech.elderly.entity.*;
import sptech.elderly.web.dto.endereco.EnderecoMapper;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class UsuarioMapper {

    public static List<UsuarioConsultaDto> toDto(List<UsuarioEntity> usuarios) {
        List<UsuarioConsultaDto> usuarioConsultaDtos = usuarios.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());

        return usuarioConsultaDtos;
    }

    public static UsuarioConsultaDto toDto(UsuarioEntity usuario) {
        UsuarioConsultaDto dto = new UsuarioConsultaDto();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDocumento(usuario.getDocumento());
        dto.setTipoUsuario(usuario.getTipoUsuario().getNome());
        dto.setGenero(usuario.getGenero() != null ? usuario.getGenero().getNome() : "Sem Gênero");


        if (usuario.getResidencias() != null && !usuario.getResidencias().isEmpty()) {
            Residencia residencia = usuario.getResidencias().get(0);
            Endereco endereco = residencia.getEndereco();

            if (endereco != null) {
                dto.setEndereco(EnderecoMapper.toDto(endereco));
            }
        }

        List<String> especialidades = mapCurriculosToEspecialidades(usuario.getCurriculos());
        dto.setEspecialidades(especialidades);

        return dto;
    }

    private static List<String> mapCurriculosToEspecialidades(List<Curriculo> curriculos) {
        return curriculos.stream()
                .map(curriculo -> curriculo.getEspecialidade())
                .map(Especialidade::getNome)
                .filter(nome -> nome != null && !nome.isEmpty())
                .collect(Collectors.toList());
    }

    public static List<ColaboradorOutput> ofColaborador(List<UsuarioEntity> users){
        return users.stream()
                .filter(usuario -> usuario.getTipoUsuario().getId() == 2)
                .map(usuario -> {
                    EnderecoOutput enderecoOutput = usuario.getResidencias().isEmpty() ? null : EnderecoMapper.toDto(usuario.getResidencias().get(0).getEndereco());

                    return new ColaboradorOutput(
                            usuario.getId(),
                            usuario.getNome(),
                            usuario.getEmail(),
                            usuario.getDocumento(),
                            enderecoOutput,
                            mapCurriculosToEspecialidades(usuario.getCurriculos())
                    );
                })
                .collect(Collectors.toList());
    }
}
