package br.crja.com.projetocrjaapi.controllers.advice;

import br.crja.com.projetocrjaapi.model.exception.ErrorAPI;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorAPI handleValidException(MethodArgumentNotValidException ex) {
        List<String> erros = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return ErrorAPI.builder().errors(erros).status(HttpStatus.BAD_REQUEST.value()).build();
    }

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<ErrorAPI> handleAllUncaughtException(ResponseStatusException ex) {
        ErrorAPI errorAPI = ErrorAPI.builder()
                .errors(Collections.singletonList(ex.getReason()))
                .status(ex.getStatus().value()).build();
        return ResponseEntity.status(ex.getStatus()).body(errorAPI);
    }


    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<ErrorAPI> handleAllUncaughtException(HttpRequestMethodNotSupportedException ex) {
        ErrorAPI errorAPI = ErrorAPI.builder()
                .errors(Collections.singletonList(ex.getMessage()))
                .status(HttpStatus.METHOD_NOT_ALLOWED.value()).build();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorAPI);
    }


    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public ResponseEntity<ErrorAPI> handleAllUncaughtException(HttpMessageNotReadableException ex) {
        ErrorAPI errorAPI = ErrorAPI.builder()
                .errors(Collections.singletonList(ex.getMessage()))
                .status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).build();
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body(errorAPI);
    }

}
