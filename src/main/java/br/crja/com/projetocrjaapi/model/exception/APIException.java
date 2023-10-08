package br.crja.com.projetocrjaapi.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class APIException extends ResponseStatusException {


    public APIException(HttpStatus status) {
        super(status);
    }

    public APIException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public APIException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }

    public APIException(int rawStatusCode, String reason, Throwable cause) {
        super(rawStatusCode, reason, cause);
    }
}
