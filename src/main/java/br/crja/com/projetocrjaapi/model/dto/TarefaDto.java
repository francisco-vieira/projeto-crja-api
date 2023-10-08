package br.crja.com.projetocrjaapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TarefaDto implements Serializable {
    private Long id;
    @NotBlank(message = "{validation.title.nome}")
    @Size(min = 2, max = 50, message = "{validation.title.size}")
    private String nomeTarefa;
    @NotNull(message = "{validation.title.preco}")
    private BigDecimal custo;
    @JsonFormat(pattern = "dd/MM/yyyy")
    @NotNull(message = "{validation.title.data}")
    private LocalDate dataLimite;
    private int ordemApresentacao;
}
