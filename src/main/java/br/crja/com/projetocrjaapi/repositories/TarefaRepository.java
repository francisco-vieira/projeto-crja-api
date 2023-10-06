package br.crja.com.projetocrjaapi.repositories;

import br.crja.com.projetocrjaapi.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {


    Integer findByOrdemApresentacao(Integer ordem);
    Optional<Tarefa> findByNomeTarefa(String descricao);

    @Query(value = "SELECT MAX(id) FROM Tarefa", nativeQuery = true)
    Integer findByMaxId();

}
