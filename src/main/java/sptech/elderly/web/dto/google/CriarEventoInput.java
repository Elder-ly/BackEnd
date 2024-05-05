package sptech.elderly.web.dto.google;

import com.google.api.client.util.DateTime;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CriarEventoInput(
        @NotBlank String nomeProposta,
        @NotBlank @Email String emailCliente,
        @NotBlank @Email String emailFuncionario,
        @NotNull DateTime dataHoraInicio,
        @NotNull DateTime dataHoraFim,
        String recorrencia,
        String descricao
) {
}
