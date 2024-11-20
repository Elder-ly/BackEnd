package sptech.elderly.web.dto.google;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter @AllArgsConstructor
public class EventoConsultaDTO {
    private String id;
    private String nomeProposta;
    private String emailCliente;
    private String emailFuncionario;
    private String dataHoraInicio;
    private String dataHoraFim;
    private List<String> recorrencia;
    private String descricao;

    public String getRecorrencia() {
        return recorrencia != null
                ? recorrencia.get(0)
                : null;
    }
}
