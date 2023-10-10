package br.crja.com.projetocrjaapi.utils;

import br.crja.com.projetocrjaapi.model.enums.ELanguage;

import java.util.HashMap;
import java.util.Map;

public class MessageUtils {

    private final ELanguage language;
    private final Map<String, String> lang = new HashMap<>();

    private MessageUtils(final ELanguage language) {
        this.language = language;
    }

    public static MessageUtils getInstance(final ELanguage language) {
        return new MessageUtils(language);
    }

    public String language() {
        return this.language.getValue();
    }

    public String message(String key) {
        switch (language) {
            case ENGLISH:
                return en(key);
            case PORTUGUESE:
                return ptBR(key);
            default:
                throw new IllegalArgumentException("Not found");
        }
    }

    private String ptBR(String key) {
        lang.put("not-found", "não encontrado");
        lang.put("invalid-date", "data inválida");
        lang.put("exists", "já existe");
        return lang.get(key);
    }

    private String en(String key) {
        lang.put("not-found", "not found");
        lang.put("invalid-date", "Invalid date");
        lang.put("exists", "already exists");
        return lang.get(key);
    }

}
