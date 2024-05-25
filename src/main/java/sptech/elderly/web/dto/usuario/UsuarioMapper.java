package sptech.elderly.web.dto.usuario;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sptech.elderly.entity.*;
import sptech.elderly.web.dto.endereco.EnderecoMapper;
import sptech.elderly.web.dto.endereco.EnderecoOutput;

import java.util.List;
import java.util.stream.Collectors;

@Component @RequiredArgsConstructor
public class UsuarioMapper {

    private final ModelMapper mapper;

    public UsuarioEntity mapearEntidade(CriarUsuarioInput input){
        return mapper.map(input, UsuarioEntity.class);
    }

    public static List<UsuarioConsultaDto> toDto(List<UsuarioEntity> usuarios) {
        return usuarios.stream()
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public static UsuarioConsultaDto toDto(UsuarioEntity usuario) {
        UsuarioConsultaDto dto = new UsuarioConsultaDto();

        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDocumento(usuario.getDocumento());
        dto.setDataNascimento(usuario.getDataNascimento());
        dto.setBiografia(usuario.getBiografia());
        dto.setFotoPerfil(usuario.getFotoPerfil());
        dto.setTipoUsuario(usuario.getTipoUsuario().getId());
        dto.setGenero(usuario.getGenero() != null ? usuario.getGenero().getId() : null);

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
        if (curriculos == null){
            return null;
        }

        return curriculos.stream()
                .map(Curriculo::getEspecialidade)
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
                            usuario.getDataNascimento(),
                            enderecoOutput,
                            mapCurriculosToEspecialidades(usuario.getCurriculos())
                    );
                })
                .collect(Collectors.toList());
    }
}