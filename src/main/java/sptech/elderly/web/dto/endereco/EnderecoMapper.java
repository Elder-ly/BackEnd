package sptech.elderly.web.dto.endereco;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import sptech.elderly.entity.Endereco;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    public Endereco ofEndereco(CriarEnderecoInput novoEndereco);

    Endereco toEntity(AtualizarEnderecoInput input);

    EnderecoConsultaDto toDto(Endereco endereco);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Endereco partialUpdate(AtualizarEnderecoInput input, @MappingTarget Endereco endereco);
}