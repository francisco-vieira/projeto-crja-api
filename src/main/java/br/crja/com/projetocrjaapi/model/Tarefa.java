package br.crja.com.projetocrjaapi.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;



@Data
@Entity
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Tarefa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_tarefa_id")
    @SequenceGenerator(name = "seq_tarefa_id", sequenceName = "sequence_tarefa")
    private Long id;
    @Column(nullable = false, unique = true)
    private String nomeTarefa;
    @Column(nullable = false)
    private BigDecimal custo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @Column(nullable = false)
    private LocalDate dataLimite;
    private Integer ordemApresentacao;

}
