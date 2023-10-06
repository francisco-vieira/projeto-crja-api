package br.crja.com.projetocrjaapi.service.ipack;

import java.util.List;

public interface ServiceAPI<T> {
    T findById(Long id);
    List<T> findAll();

    T salvar(T t);
    void delete(Long id);
}
