package sptech.elderly.web.dto.usuario;

import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;
//import sptech.elderly.entity.ClienteOutput;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    public UsuarioEntity criarCliente(CriarClienteInput novoUser);

    public TipoUsuario mapTipoUsuario(Integer tipoUsuario);

    public Genero mapGenero(Integer genero);

//    UsuarioEntity toEntity(ClienteOutput clienteOutput);
//
//    ClienteOutput toDto(UsuarioEntity usuarioEntity);
//
//    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
//    UsuarioEntity partialUpdate(ClienteOutput clienteOutput, @MappingTarget UsuarioEntity usuarioEntity);
}