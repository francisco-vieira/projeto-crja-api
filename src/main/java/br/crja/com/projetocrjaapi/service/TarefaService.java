package br.crja.com.projetocrjaapi.service;

import br.crja.com.projetocrjaapi.model.Tarefa;
import br.crja.com.projetocrjaapi.model.dto.TarefaDto;
import br.crja.com.projetocrjaapi.model.exception.APIException;
import br.crja.com.projetocrjaapi.repositories.TarefaRepository;
import br.crja.com.projetocrjaapi.service.ipack.ServiceAPI;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

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
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));
    }

    @Override
    public List<Tarefa> findAll() {
        return this.repository.findAllByOrderByOrdemApresentacao();
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) throws APIException {
        Integer ordem = this.repository.findByMaxId();

        ordem = Objects.isNull(ordem) ? 0 : ordem + 1;

        tarefa.setOrdemApresentacao(ordem);
        Optional<Tarefa> opt = this.repository.findByNomeTarefa(tarefa.getNomeTarefa());
        boolean existe = opt.isPresent();

        if (Objects.nonNull(tarefa.getId()) || existe) {
            long id = tarefa.getId() == null ? 0 : tarefa.getId();
            Tarefa rs = this.repository.findById(id).orElse(null);
            if (Objects.nonNull(rs)) {
                this.existe(existe && !Objects.equals(opt.get().getId(), tarefa.getId()), tarefa.getNomeTarefa());
                tarefa.setId(rs.getId());
                tarefa.setOrdemApresentacao(rs.getOrdemApresentacao());
            } else {
                this.existe(existe, tarefa.getNomeTarefa());
            }
        }

        try {
            return this.repository.save(tarefa);
        } catch (APIException e) {
            throw new APIException(e.getStatus(), e.getMessage());
        }
    }

    public void salvarOrdem(Integer previousIndex, Integer currentIndex) {

        if(Objects.equals(previousIndex, currentIndex)){
            return;
        }

        List<Tarefa> tarefaList = this.repository
                .findAllByOrdemApresentacaoIn(
                        Arrays.asList(previousIndex, currentIndex)
                );

        List<Tarefa> aux = new ArrayList<>();
        Optional<Tarefa> currennt = tarefaList.stream().filter(f -> f.getOrdemApresentacao().equals(currentIndex)).findFirst();
        if (currennt.isPresent()) {
            Tarefa c = currennt.get();
            c.setOrdemApresentacao(previousIndex);
            aux.add(c);
        }

        Optional<Tarefa> previous = tarefaList.stream().filter(f -> f.getOrdemApresentacao().equals(previousIndex)).findFirst();
        if (previous.isPresent()) {
            Tarefa c = previous.get();
            c.setOrdemApresentacao(currentIndex);
            aux.add(c);
        }

        this.repository.saveAll(aux);
    }

    private void existe(boolean existe, String nomeTarefa) {
        if (existe) {
            throw new APIException(HttpStatus.CONFLICT, "Tarefa ".concat(nomeTarefa).concat(" já existe"));
        }
    }

    public Tarefa convert(TarefaDto source) {
        Tarefa target = new Tarefa();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public TarefaDto convert(Tarefa source) {
        TarefaDto target = new TarefaDto();
        BeanUtils.copyProperties(target, source);
        return target;
    }

    @Override
    public void delete(Long id) {
        if (this.repository.existsById(id)) {
            this.repository.deleteById(id);
        }

    }
}
