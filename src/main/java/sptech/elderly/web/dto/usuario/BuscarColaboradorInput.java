package sptech.elderly.web.dto.usuario;

import com.google.api.client.util.DateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.List;

public record BuscarColaboradorInput(List<@NotBlank String> especialidades,
                                     @NotNull DateTime dataHoraInicio,
                                     @NotNull DateTime dataHoraFim) {
}
