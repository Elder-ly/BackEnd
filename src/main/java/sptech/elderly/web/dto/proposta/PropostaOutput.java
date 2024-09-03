package sptech.elderly.web.dto.proposta;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @AllArgsConstructor @NoArgsConstructor
public class PropostaOutput {
    private Long id;
    private String descricao;
    private LocalDateTime dataHoraInicio;
    private LocalDateTime dataHoraFim;
    private BigDecimal preco;
    private Boolean aceita;
}
