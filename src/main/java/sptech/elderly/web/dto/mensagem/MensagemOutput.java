package sptech.elderly.web.dto.mensagem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter @NoArgsConstructor @AllArgsConstructor
public class MensagemOutput {
    private Integer id;
    private String conteudo;
    private LocalDateTime dataHora;
    private UsuarioConversaOutput remetente;
    private UsuarioConversaOutput destinatario;
}
