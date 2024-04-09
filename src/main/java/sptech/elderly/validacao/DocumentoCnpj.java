package sptech.elderly.validacao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;

@Getter @NoArgsConstructor
public class DocumentoCnpj {
    @CNPJ
    public static String cnpj;
}
