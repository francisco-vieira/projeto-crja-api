package br.crja.com.projetocrjaapi.model.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum ELanguage {

    PORTUGUESE(1, "Portuguese"),
    ENGLISH(2, "English");
    private final Integer code;
    private final String value;

    ELanguage(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    ELanguage value(Object value) {
        return Arrays.stream(values())
                .filter(f-> f.value.equalsIgnoreCase(String.valueOf(value)) || f.code.equals(value))
                .findFirst()
                .orElseThrow(()->new IllegalArgumentException("Language not found"));

    }

}
