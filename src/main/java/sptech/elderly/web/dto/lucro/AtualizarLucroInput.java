package sptech.elderly.web.dto.lucro;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link sptech.elderly.entity.Lucro}
 */
public record AtualizarLucroInput(@NotNull @PositiveOrZero BigDecimal lucro) implements Serializable {
}