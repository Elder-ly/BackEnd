package sptech.elderly.web.dto.usuario;

import org.mapstruct.Mapper;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface FuncionarioMapper {
    public UsuarioEntity criarFuncionario(CriarFuncionario novoFuncionario);

    public TipoUsuario mapTipoUsuario(Integer tipoUsuario);

    public Genero mapGenero(Integer genero);
}