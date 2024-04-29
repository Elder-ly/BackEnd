package sptech.elderly.web.dto.usuario;

import org.mapstruct.*;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    public UsuarioEntity criarCliente(CriarClienteInput novoUser);

    public TipoUsuario mapTipoUsuario(Integer tipoUsuario);

    public Genero mapGenero(Integer genero);

    AtualizarClienteInput toDto(UsuarioEntity usuarioEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UsuarioEntity partialUpdate(AtualizarClienteInput input, @MappingTarget UsuarioEntity usuarioEntity);

    UsuarioEntity toEntity(AtualizarClienteInput atualizarClienteInput);
}