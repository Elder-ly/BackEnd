package sptech.elderly.web.dto.endereco;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sptech.elderly.entity.Endereco;
import sptech.elderly.entity.UsuarioEntity;

@RequiredArgsConstructor @Component
public class EnderecoMapper {

    private final ModelMapper mapper;

    public Endereco mapearEndereco(CriarEnderecoInput input){
        return mapper.map(input, Endereco.class);
    }

    public static EnderecoOutput toDto(Endereco endereco){
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
}