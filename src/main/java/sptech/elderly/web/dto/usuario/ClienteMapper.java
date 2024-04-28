package sptech.elderly.web.dto.usuario;

import org.mapstruct.Mapper;
import sptech.elderly.entity.Genero;
import sptech.elderly.entity.TipoUsuario;
import sptech.elderly.entity.UsuarioEntity;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    public UsuarioEntity criarCliente(CriarCliente novoUser);

    public TipoUsuario mapTipoUsuario(Integer tipoUsuario);

    public Genero mapGenero(Integer genero);



}