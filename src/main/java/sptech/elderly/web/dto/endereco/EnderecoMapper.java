package sptech.elderly.web.dto.endereco;

import sptech.elderly.entity.Endereco;

public class EnderecoMapper {

    public static Endereco ofEndereco(CriarEnderecoInput novoEndereco){
        Endereco endereco = new Endereco();

        endereco.setCep(novoEndereco.cep());
        endereco.setLogradouro(novoEndereco.logradouro());
        endereco.setNumeroCasa(novoEndereco.numeroCasa());
        endereco.setComplemento(novoEndereco.complemento());
        endereco.setCidade(novoEndereco.cidade());
        endereco.setUf(novoEndereco.uf());

        return endereco;
    }
}
