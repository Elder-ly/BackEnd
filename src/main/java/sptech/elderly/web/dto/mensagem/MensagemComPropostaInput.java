package sptech.elderly.web.dto.mensagem;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sptech.elderly.web.dto.proposta.PropostaInput;

public record MensagemComPropostaInput(@NotNull Integer remetenteId,
                                       @NotNull Integer destinatarioId,
                                       @NotBlank String conteudo,
                                       @NotNull @Valid PropostaInput proposta) {
}
