package br.crja.com.projetocrjaapi.controllers;


import br.crja.com.projetocrjaapi.model.Tarefa;
import br.crja.com.projetocrjaapi.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/tarefas")
public class TarefaController {

    private final TarefaService service;

    @Autowired
    public TarefaController(TarefaService service) {
        this.service = service;
    }


    @GetMapping("todos")
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<List<Tarefa>> findAll() {
        List<Tarefa> list = this.service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<Tarefa> findById(@RequestParam("id") Long id) {
        Tarefa entity = this.service.findById(id);
        return ResponseEntity.ok(entity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Tarefa> post(@RequestBody Tarefa tarefa) {
        Tarefa entity = this.service.salvar(tarefa);
        return ResponseEntity.ok(entity);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@RequestParam("id") Long id) {
        this.service.delete(id);
        return ResponseEntity.ok().build();
    }


}
