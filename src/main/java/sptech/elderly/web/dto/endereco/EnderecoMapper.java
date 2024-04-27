package sptech.elderly.web.dto.endereco;

import org.mapstruct.Mapper;
import sptech.elderly.entity.Endereco;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    public Endereco ofEndereco(CriarEnderecoInput novoEndereco);
}
