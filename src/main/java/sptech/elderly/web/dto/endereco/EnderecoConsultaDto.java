package sptech.elderly.web.dto.endereco;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link sptech.elderly.entity.Endereco}
 */
@Getter @Setter @NoArgsConstructor
public class EnderecoConsultaDto implements Serializable {
    String cep;
    String logradouro;
    String bairro;
    String numero;
    String complemento;
    String cidade;
    String uf;
}