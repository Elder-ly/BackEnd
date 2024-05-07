package sptech.elderly.web.controller;

import com.google.api.services.calendar.model.Event;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.service.GoogleCalendarService;
import sptech.elderly.util.ListaObj;
import sptech.elderly.web.dto.google.CriarEventoInput;
import sptech.elderly.web.dto.google.EventoConsultaDTO;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequestMapping("/calendarios")
public class CalendarioController {
    private final GoogleCalendarService service;

    public CalendarioController(GoogleCalendarService service) {
        this.service = service;
    }

    @PostMapping("/eventos")
    public EventoConsultaDTO inserirEvento(
            @RequestHeader String accessToken,
            @RequestBody CriarEventoInput eventoInput
            ) throws GeneralSecurityException, IOException {

        return service.inserirEvento(
                accessToken,
                eventoInput.nomeProposta(),
                eventoInput.emailCliente(),
                eventoInput.emailFuncionario(),
                eventoInput.dataHoraInicio(),
                eventoInput.dataHoraFim(),
                eventoInput.recorrencia(),
                eventoInput.descricao()
        );
    }

    @GetMapping("/eventos")
    public List<EventoConsultaDTO> listarEventosCuidador(
            @RequestHeader String accessToken,
            @RequestParam(value = "ordenarPor", required = false, defaultValue = "dataHoraInicio") String ordenarPor
    ) throws IOException, GeneralSecurityException {
        return service.listarEventos(accessToken, ordenarPor);
    }
}
