package sptech.elderly.web.dto.lucro;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link sptech.elderly.entity.Lucro}
 */
public record LucroOutput(Long id, BigDecimal lucro) implements Serializable {
}