package sptech.elderly.validacao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

@Getter @NoArgsConstructor
public class DocumentoCpf {
    @CPF
    public static String cpf;
}
