package br.crja.com.projetocrjaapi.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;



@Data
@Entity
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Tarefa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
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
