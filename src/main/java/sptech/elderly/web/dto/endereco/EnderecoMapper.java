package sptech.elderly.web.dto.endereco;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import sptech.elderly.entity.Endereco;

@Component @RequiredArgsConstructor
public class EnderecoMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public Endereco mapearEndereco(CriarEnderecoInput input){
        return mapper.map(input, Endereco.class);
    }

    public static Endereco atualizarEndereco(CriarEnderecoInput input, Integer id){
        Endereco endereco = new Endereco();

        endereco.setId(id);
        endereco.setCep(input.cep());
        endereco.setLogradouro(input.logradouro());
        endereco.setComplemento(input.complemento());
        endereco.setBairro(input.bairro());
        endereco.setNumero(input.numero());
        endereco.setCidade(input.cidade());
        endereco.setUf(input.uf());

        return endereco;
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