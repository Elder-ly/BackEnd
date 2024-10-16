package sptech.elderly.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class ListEmptyException extends RuntimeException {

    public ListEmptyException() {
        super(); // No message passed
    }

}
