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
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Tarefa não encontrada!"));
    }

    @Override
    public List<Tarefa> findAll() {
        return this.repository.findAll();
    }

    @Override
    public Tarefa salvar(Tarefa tarefa) throws APIException{
        Integer ordem = this.repository.findByMaxId();

        ordem = Objects.isNull(ordem) ? 1 : ordem + 1;

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
        }catch (APIException e){
            throw  new APIException(e.getStatus(), e.getMessage());
        }
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
