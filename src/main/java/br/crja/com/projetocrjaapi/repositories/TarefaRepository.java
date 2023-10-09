package br.crja.com.projetocrjaapi.repositories;

import br.crja.com.projetocrjaapi.model.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Repository
public interface TarefaRepository extends JpaRepository<Tarefa, Long> {


    List<Tarefa> findAllByOrdemApresentacaoIn(Collection<Integer> listOrdem);
    Optional<Tarefa> findByNomeTarefa(String descricao);

    @Query(value = "SELECT MAX(ordem_apresentacao) FROM Tarefa f", nativeQuery = true)
    Integer findByMaxId();

}
