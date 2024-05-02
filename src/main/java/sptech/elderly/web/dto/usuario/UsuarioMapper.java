package sptech.elderly.web.dto.usuario;


import sptech.elderly.entity.*;
import sptech.elderly.web.dto.endereco.EnderecoConsultaDto;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {
    public static List<UsuarioConsultaDto> toDto(List<UsuarioEntity> usuarios) {
        List<UsuarioConsultaDto> usuarioConsultaDtos = usuarios.stream()
                .map(UsuarioMapper::toDto)  // Use a referência do método toDto da própria classe UsuarioMapper
                .collect(Collectors.toList());

        return usuarioConsultaDtos;
    }

    public static UsuarioConsultaDto toDto(UsuarioEntity usuario) {
        UsuarioConsultaDto dto = new UsuarioConsultaDto();

        // Mapeamento dos campos simples
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setDocumento(usuario.getDocumento());
        dto.setTipoUsuario(usuario.getTipoUsuario().getNome());
        dto.setGenero(usuario.getGenero() != null ? usuario.getGenero().getNome() : "Sem Gênero");

        // Mapeamento de Endereco
        if (usuario.getResidencias() != null && !usuario.getResidencias().isEmpty()) {
            // Assume que a primeira residencia representa o endereco do usuario
            Residencia residencia = usuario.getResidencias().get(0);
            Endereco endereco = residencia.getEndereco();
            if (endereco != null) {
                dto.setEndereco(toEnderecoDto(endereco));
            }
        }

        // Mapeamento de especialidades
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

    private static EnderecoConsultaDto toEnderecoDto(Endereco endereco) {
        EnderecoConsultaDto dto = new EnderecoConsultaDto();
        dto.setCep(endereco.getCep());
        dto.setLogradouro(endereco.getLogradouro());
        dto.setNumero(endereco.getNumero());
        dto.setComplemento(endereco.getComplemento());
        dto.setBairro(endereco.getBairro());
        dto.setCidade(endereco.getCidade());
        dto.setUf(endereco.getUf());
        return dto;
    }
}
