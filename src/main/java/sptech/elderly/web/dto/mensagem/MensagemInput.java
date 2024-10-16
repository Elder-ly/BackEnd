package sptech.elderly.web.dto.mensagem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MensagemInput(@NotNull Long remetenteId,
                            @NotNull Long destinatarioId,
                            @NotBlank String conteudo) {
}
