package br.crja.com.projetocrjaapi.model.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
@Getter
@Builder
@AllArgsConstructor
public class ErrorAPI {
    private int status;
    private List<String> errors;
    public ErrorAPI(String error, int status) {
        this.status = status;
        this.errors = Collections.singletonList(error);
    }

}
