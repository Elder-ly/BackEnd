package sptech.elderly.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DadosDuplicadosException extends RuntimeException {

    public DadosDuplicadosException(String recurso, String campo, Object valor){
        super("O recurso %s com o campo %s de valor %s já existe.".formatted(recurso, campo, valor));
    }
}
