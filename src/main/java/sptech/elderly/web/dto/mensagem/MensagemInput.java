package sptech.elderly.web.dto.mensagem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MensagemInput(@NotNull Integer remetenteId,
                            @NotNull Integer destinatarioId,
                            @NotBlank String conteudo) {
}
