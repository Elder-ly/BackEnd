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

    static EnderecoOutput toDto(Endereco endereco){
        return new EnderecoOutput(
                endereco.getId(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getBairro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCidade(),
                endereco.getUf()
        );
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Endereco partialUpdate(AtualizarEnderecoInput input, @MappingTarget Endereco endereco);

    Endereco toEntity(EnderecoOutput enderecoOutput);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Endereco partialUpdate(EnderecoOutput enderecoOutput, @MappingTarget Endereco endereco);
}