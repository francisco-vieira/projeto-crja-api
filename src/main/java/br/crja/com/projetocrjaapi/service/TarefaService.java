package br.crja.com.projetocrjaapi.service;

import br.crja.com.projetocrjaapi.model.Tarefa;
import br.crja.com.projetocrjaapi.repositories.TarefaRepository;
import br.crja.com.projetocrjaapi.service.ipack.ServiceAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TarefaService implements ServiceAPI<Tarefa> {

    private final TarefaRepository repository;

    @Autowired
    public TarefaService(TarefaRepository repository) {
        this.repository = repository;
    }


    @Override
    public Tarefa findById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));
    }

    @Override
    public List<Tarefa> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) {
        Integer ordem = this.repository.findByMaxId();

        if (Objects.isNull(ordem)) {
            ordem = 1;
        } else {
            ordem += 1;
        }

        Optional<Tarefa> nome = this.repository.findByNomeTarefa(tarefa.getNomeTarefa());
        boolean existe = nome.isPresent();

        if (Objects.nonNull(tarefa.getId())) {
            Tarefa rs = this.repository.findById(tarefa.getId()).orElse(null);
            if (Objects.nonNull(rs)) {
                tarefa.setId(rs.getId());
                tarefa.setOrdemApresentacao(rs.getOrdemApresentacao());
                tarefa.setDataLimite(rs.getDataLimite());
                if (existe && !Objects.equals(nome.get().getId(), tarefa.getId())) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Tarefa já existe");
                }
            } else {
                tarefa.setOrdemApresentacao(ordem);
                if (existe) {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Tarefa já existe");
                }
            }
        }


        return this.repository.save(tarefa);
    }

    @Override
    public void delete(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        }

    }
}
