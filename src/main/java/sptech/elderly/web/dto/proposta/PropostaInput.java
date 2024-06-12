package sptech.elderly.web.dto.proposta;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PropostaInput (@NotBlank String descricao,
                             @NotNull LocalDateTime dataHoraInicio,
                             @NotNull LocalDateTime dataHoraFim,
                             @NotNull BigDecimal preco
){
}
