package sptech.elderly.web.dto.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sptech.elderly.web.dto.proposta.PropostaOutput;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor @AllArgsConstructor
public class MensagemComPropostaOutput {
    private Long id;
    private String conteudo;
    private LocalDateTime dataHora;
    private UsuarioMensagemOutput remetente;
    private UsuarioMensagemOutput destinatario;
    private PropostaOutput proposta;
}
