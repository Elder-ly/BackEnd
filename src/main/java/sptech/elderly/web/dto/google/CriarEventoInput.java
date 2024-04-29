package sptech.elderly.web.dto.google;

import com.google.api.client.util.DateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CriarEventoInput(
        @NotBlank String accessToken,
        @NotBlank String nomeProposta,
        @NotBlank @Email String emailCliente,
        @NotBlank @Email String emailFuncionario,
        @NotNull DateTime dataHoraInicio,
        @NotNull DateTime dataHoraFim
) {
}
