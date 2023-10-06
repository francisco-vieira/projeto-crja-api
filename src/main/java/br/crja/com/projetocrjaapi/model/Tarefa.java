package br.crja.com.projetocrjaapi.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;



@Data
@Entity
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Tarefa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nomeTarefa;
    private BigDecimal custo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataLimite;
    private int ordemApresentacao;
}
