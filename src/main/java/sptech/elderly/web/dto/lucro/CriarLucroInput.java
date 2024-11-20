package sptech.elderly.web.dto.lucro;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link sptech.elderly.entity.Lucro}
 */
public record CriarLucroInput(@NotNull @PositiveOrZero BigDecimal lucro,
                              @NotNull Long usuario) implements Serializable {
}